package org.lemsml.codegen;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
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


	@Test
	public void testDomGen() throws Throwable {

		Lems domainDefs = new LEMSCompilerFrontend(
				getLocalFile("/examples/domo/fooml.xml"))
				.generateLEMSDocument();

		// The compiler will have a "domain specific library" backend
		ST stTest = merge(domainDefs, "fooML");
		System.out.println(stTest.render());

		// We then want to unmarshall a DS model (defined in xml) using the
		// generated (hybrid domain/lems) objects (of which we have literal
		// pregenerated examples FooML.java Foo.java, etc)
		File model = getLocalFile("/examples/domo/foo.xml");

		jaxbContext = JAXBContext.newInstance("org.lemsml.codegen.domo");
		//genSchema(jaxbContext); //TODO: see comment on method
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		FooML fooModel = (FooML) jaxbUnmarshaller.unmarshal(model);

		//TODO: do we always implicitly "include" the defs?
		//      what about constants / ... ?
		fooModel.getComponentTypes().addAll(domainDefs.getComponentTypes());
		new LEMSSemanticAnalyser(fooModel).analyse();

		testTypes(fooModel);
		testEvaluation(fooModel);
		testMarshalling(fooModel);
	}

	public void testTypes(FooML fooModel) throws LEMSCompilerException {
		assertEquals(1, fooModel.getFoos().size());
		assertEquals(0, fooModel.getBars().size());
		assertEquals(1, fooModel.getAllBars().size());
		assertEquals(2, fooModel.getAllBases().size());

		Foo foo0 = (Foo) fooModel.getComponentById("foo0");
		assertEquals(2, foo0.getFooBazs().size());
		assertEquals("10", foo0.getFooBar().getParameterValue("pBar"));
	}

	public void testEvaluation(FooML fooModel) throws LEMSCompilerException {

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

	public void testMarshalling(FooML fooModel)
			throws JAXBException, PropertyException, IOException {

		File tmpFile = File.createTempFile("test", ".xml");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		fooModel.getComponentTypes().clear();
		marshaller.marshal(fooModel, tmpFile);
		System.out.println(Files.toString(tmpFile, Charsets.UTF_8));
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
