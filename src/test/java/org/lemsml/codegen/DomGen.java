package org.lemsml.codegen;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.codegen.domo.Bar;
import org.lemsml.codegen.domo.Base;
import org.lemsml.codegen.domo.Baz;
import org.lemsml.codegen.domo.Foo;
import org.lemsml.codegen.domo.FooML;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.compiler.semantic.LEMSSemanticAnalyser;
import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.Lems;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class DomGen {

	private JAXBContext jaxbContext;
	private FooML fooModel;
	private Lems domainDefs;

	@Before
	public void setUp() throws Throwable, JAXBException {
		domainDefs = new LEMSCompilerFrontend(
				getLocalFile("/examples/domo/FooML.xml"))
				.generateLEMSDocument();

		// We then want to unmarshall a DS model (defined in xml) using the
		// generated (hybrid domain/lems) objects (of which we have literal
		// pregenerated examples FooML.java Foo.java, etc)
		File model = getLocalFile("/examples/domo/foo.xml");

		jaxbContext = JAXBContext.newInstance("org.lemsml.codegen.domo");
		//genSchema(jaxbContext); //TODO: see comment on method
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		fooModel = (FooML) jaxbUnmarshaller.unmarshal(model);

		//TODO: do we always implicitly "include" the defs?
		//      what about constants / ... ?
		fooModel.getComponentTypes().addAll(domainDefs.getComponentTypes());
		new LEMSSemanticAnalyser(fooModel).analyse();
	}

	@Test
	public void testDoMoGen() {
		// The compiler will have a "domain specific library" backend
		ST stTest = merge(domainDefs, "fooML");
		System.out.println(stTest.render());
	}

	@Test
	public void testTypes() throws LEMSCompilerException {
		assertEquals(1, fooModel.getFoos().size());
		assertEquals(0, fooModel.getBars().size());
		assertEquals(6, fooModel.getAllOfType(Bar.class).size());
		assertEquals(5, fooModel.getAllOfType(Baz.class).size());
		assertEquals(10, fooModel.getAllOfType(Base.class).size());

		Foo foo0 = (Foo) fooModel.getComponentById("foo0");

		assertEquals(foo0, fooModel.getAllOfType(Foo.class).get(0));
		assertEquals(2, foo0.getFooBazs().size());
		assertEquals("10", foo0.getFooBar().getParameterValue("pBar"));
	}

	@Test
	public void testEvaluation() throws LEMSCompilerException {

		Component foo0 = fooModel.getComponentById("foo0");
		Component barInFoo0 = fooModel.getComponentById("barInFoo");

		Double pBar = Double.valueOf(fooModel.getFoos().get(0).getFooBar().getPBar());
		assertEquals(pBar, foo0
							.getChildren()
							.get(0)
							.getScope()
							.evaluate("pBar").getValue().doubleValue(), 1e-12);
		assertEquals(0.1, barInFoo0
							.getScope()
							.evaluate("dpBar").getValue().doubleValue(), 1e-12);

		//testing synch
		//changing par via lems api
		foo0.withParameterValue("pFoo", "3");
		assertEquals(0.3, barInFoo0
							.getScope()
							.evaluate("dpBar").getValue().doubleValue(), 1e-12);

		//changing par via domain api
		((Foo) foo0).setPFoo("4");

		assertEquals("4", ((Foo) foo0).getPFoo());
		assertEquals(0.4, barInFoo0
							.getScope()
							.evaluate("dpBar").getValue().doubleValue(), 1e-12);
	}

	@Test
	public void testMarshalling()
			throws JAXBException, PropertyException, IOException {

		File tmpFile = File.createTempFile("test", ".xml");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		fooModel.getComponentTypes().clear();
		eraseTypes(fooModel.getComponents()); //TODO: extremely ugly hack

		marshaller.marshal(fooModel, tmpFile);
		System.out.println(Files.toString(tmpFile, Charsets.UTF_8));
	}

	//TODO: argh! @XmlTransient in ext.Comp isn't overriding type from (on-ext)Comp
	void eraseTypes(List<Component> list){
		for(Component comp : list){
			eraseTypes(comp.getComponent());
			comp.withType(null);
		}

	}

	// @Test
	// TODO: breaks if FooML extends Lems
	// cause: ComplexType of Lems is anonymous:
	//  https://java.net/jira/browse/JAXB-411
	public void genSchema(JAXBContext jaxbContext) throws IOException {
		class MySchemaOutputResolver extends SchemaOutputResolver {
			public Result createOutput(String namespaceURI,
					String suggestedFileName) throws IOException {
				File file = new File(suggestedFileName);
				StreamResult result = new StreamResult(file);
				result.setSystemId(file.getAbsolutePath());
				return result;
			}
		}
		jaxbContext.generateSchema(new MySchemaOutputResolver());
	}


	protected File getLocalFile(String fname) {
		return new File(getClass().getResource(fname).getFile());
	}

	public ST merge(Lems lems, String langName) {
		URL stURL = getClass().getResource("/stringtemplate/domo.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		group.registerRenderer(String.class, new StringRenderer());
		ST stTest = group.getInstanceOf("domo");

		stTest.add("lems", lems);
		stTest.add("ml_name", langName);
		return stTest;
	}

}
