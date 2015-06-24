package org.lemsml.codegen;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.codegen.domo.FooML;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.compiler.semantic.LEMSSemanticAnalyser;
import org.lemsml.model.extended.Lems;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class DomGen {

	private JAXBContext jaxbContext;

	@Before

	@Test
	public void testDomGen() throws Throwable {

		Lems domainDefs = new LEMSCompilerFrontend(
				getLocalFile("/examples/domo/fooml.xml"))
				.generateLEMSDocument();

		// The compiler will have a "domain specific library" backend
		ST stTest = merge(domainDefs);
		System.out.println(stTest.render());

		// We then want to unmarshall a DS model (defined in xml) using the
		// generated (hybrid domain/lems) objects (of which we have literal
		// pregenerated examples FooML.java Foo.java, etc)
		File model = getLocalFile("/examples/domo/foo.xml");

		jaxbContext = JAXBContext.newInstance("org.lemsml.codegen.domo");
		//genSchema(jaxbContext); //TODO: see comment on method
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		FooML fooModel = (FooML) jaxbUnmarshaller.unmarshal(model);

		assertEquals(1, fooModel.getFoos().size());
		assertEquals(0, fooModel.getBars().size());
		assertEquals(1, fooModel.getAllBars().size());
		assertEquals(2, fooModel.getAllBases().size());

		//TODO: do we always implicitly "include" the defs?
		//      what about constants / ... ?
		fooModel.getComponentTypes().addAll(domainDefs.getComponentTypes());
		new LEMSSemanticAnalyser(fooModel).analyse();

		assertEquals(1.0, fooModel
							.getComponentById("baz0")
							.resolve("p1")
							.getValue(), 1e-12);
		assertEquals(0.0, fooModel
							.getComponentById("foo0")
							.getChildren()
							.get(0)
							.resolve("p0")
							.evaluate(), 1e-12);

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

	public ST merge(Lems lems) {
		URL stURL = getClass().getResource("/stringtemplate/domo.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		group.registerRenderer(String.class, new StringRenderer());
		ST stTest = group.getInstanceOf("domo");

		stTest.add("lems", lems);
		return stTest;
	}

}
