package org.lemsml.codegen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.codegen.fakeNML.FakeNML;
import org.lemsml.codegen.fakeNML.HHChannel;
import org.lemsml.codegen.fakeNML.HHGate;
import org.lemsml.codegen.fakeNML.HHRate;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.compiler.semantic.LEMSSemanticAnalyser;
import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Lems;
import org.lemsml.model.extended.NamedDimensionalType;
import org.lemsml.model.extended.Scope;
import org.lemsml.model.extended.Symbol;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import tec.units.ri.quantity.Quantities;

import com.xeiam.xchart.BitmapEncoder;
import com.xeiam.xchart.BitmapEncoder.BitmapFormat;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.ChartBuilder;
import com.xeiam.xchart.StyleManager.ChartTheme;

public class NMLGen {

	private JAXBContext jaxbContext;
	private FakeNML nmlDoc;
	private Lems domainDefs;

	@Before
	public void setUp() throws Throwable, JAXBException {
		domainDefs = new LEMSCompilerFrontend(
				getLocalFile("/examples/lems/example1_types.xml"))
				.generateLEMSDocument();

		// pregenerated examples FooML.java Foo.java, etc)
		File model = getLocalFile("/examples/lems/example1_comps.xml");
		jaxbContext = JAXBContext.newInstance("org.lemsml.codegen.fakeNML");
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		nmlDoc = (FakeNML) jaxbUnmarshaller.unmarshal(model);

		// TODO: do we always implicitly "include" the defs?
		// what about constants / ... ?
		nmlDoc.getComponentTypes().addAll(domainDefs.getComponentTypes());
		nmlDoc.getDimensions().addAll(domainDefs.getDimensions());
		nmlDoc.getUnits().addAll(domainDefs.getUnits());
		nmlDoc.getConstants().addAll(domainDefs.getConstants());
		new LEMSSemanticAnalyser(nmlDoc).analyse();
	}

	@Test
	public void testDoMoGen() {
		// The compiler will have a "domain specific library" backend
		ST stTest = generateDomainClasses(domainDefs, "fakeNML");
		System.out.println(stTest.render());
	}

	@Test
	public void testInfoExtractor() {
		// The compiler will have a "domain specific library" backend
		ST stTest = generateInfo(domainDefs);
		System.out.println(stTest.render());
	}

	//@Test
	public void testParsing() throws LEMSCompilerException, IOException {

		Set<HHChannel> allChannels = nmlDoc.getAllOfType(HHChannel.class);
		for (HHChannel chan : allChannels) {
			for (HHGate gate : chan.getGates()) {
				System.out.println(chan + " " + gate);

				plotRate(gate.getForward());
				plotRate(gate.getReverse());
			}
		}
	}

	private void plotRate(HHRate rate) throws LEMSCompilerException,
			IOException {
		Scope scope = rate.getScope();
		int npt = 100;
		Map<Double, Double> graph = evalInGrid(scope, scope.resolve("r"),
				scope.resolve("v"), -80., 100., npt);
		double[] xs = new double[npt];
		double[] ys = new double[npt];
		int i = 0;
		for (Entry<Double, Double> pt : graph.entrySet()) {
			xs[i] = pt.getKey();
			ys[i] = pt.getValue();
			i++;
		}
		String rateName = MessageFormat.format("[{0} channel]: gate ({1}), {2}",
				rate.getParent().getParent().getName(),
				rate.getParent().getName(),
				rate.getName());
		Chart chart = new ChartBuilder().width(800).height(600)
				.theme(ChartTheme.Matlab)
				.title(rateName)
				.xAxisTitle("V(mV)")
				.yAxisTitle("rate(1/ms)")
				.build();
		chart.getStyleManager().setPlotGridLinesVisible(false);
		chart.getStyleManager().setLegendVisible(false);
		chart.getStyleManager().setXAxisTickMarkSpacingHint(150);
		chart.getStyleManager().setYAxisTickMarkSpacingHint(150);
		chart.addSeries(rateName, xs, ys);

		String fname = MessageFormat.format("/tmp/{0}-{1}.png", rate
				.getParent().getName(), rate.getName());
		BitmapEncoder.saveBitmapWithDPI(chart, fname, BitmapFormat.PNG, 150);

	}

	private static Map<Double, Double> evalInGrid(Scope scope, Symbol toEval,
			Symbol indepVar, Double x0, Double x1, Integer npoints)
			throws LEMSCompilerException {

		Map<Double, Double> graph = new LinkedHashMap<Double, Double>();

		Unit<?> dim = ((NamedDimensionalType) indepVar.getType())
				.getUOMDimension();
		String ivName = indepVar.getName();
		Map<String, Quantity<?>> ctxt = new HashMap<String, Quantity<?>>();

		Double dx = Math.abs(x1 - x0) / npoints;
		for (int i = 0; i < npoints; i++) {
			Double x = x0 + i * dx;
			ctxt.put(ivName, Quantities.getQuantity(x, dim));
			Double fx = scope.evaluate(toEval.getName(), ctxt).getValue()
					.doubleValue();
			graph.put(x, fx);
		}
		return graph;
	}

	public ST generateDomainClasses(Lems lems, String langName) {
		URL stURL = getClass().getResource("/stringtemplate/domo.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		group.registerRenderer(String.class, new StringRenderer());
		ST stTest = group.getInstanceOf("domo");

		stTest.add("lems", lems);
		stTest.add("ml_name", langName);
		return stTest;
	}


	public ST generateInfo(Lems lems) {
		URL stURL = getClass().getResource("/stringtemplate/info.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		group.registerRenderer(String.class, new StringRenderer());
		group.registerRenderer(Symbol.class, new LemsExpressionRenderer());
		group.registerModelAdaptor(Scope.class, new ScopeAdaptor());

		ST stTest = group.getInstanceOf("info");

//		class IntermediateRepr {
//			private String name;
//			private Set<HHCell> cells;
//			public String getName() {
//				return name;
//			}
//			public void setName(String name) {
//				this.name = name;
//			}
//			public Set<HHCell> getCells() {
//				return cells;
//			}
//			public void setCells(Set<HHCell> cells) {
//				this.cells = cells;
//			}
//		}
//		IntermediateRepr ir = new IntermediateRepr();
//		ir.setName("Fake NeuroML example");
//		ir.setCells(nmlDoc.getAllOfType(HHCell.class));

		stTest.add("domo", nmlDoc);
		return stTest;
	}


	protected File getLocalFile(String fname) {
		return new File(getClass().getResource(fname).getFile());
	}

}
