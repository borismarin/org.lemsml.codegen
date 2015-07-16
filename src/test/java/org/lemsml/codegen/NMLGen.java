package org.lemsml.codegen;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.codegen.fakeNML.FakeNML;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.compiler.semantic.LEMSSemanticAnalyser;
import org.lemsml.model.extended.Lems;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

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
//		File model = getLocalFile("/examples/lems/example1_comps.xml");
//		jaxbContext = JAXBContext.newInstance("org.lemsml.codegen.fakeNML");
//		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//		nmlDoc = (FakeNML) jaxbUnmarshaller.unmarshal(model);
//
//		//TODO: do we always implicitly "include" the defs?
//		//      what about constants / ... ?
//		nmlDoc.getComponentTypes().addAll(domainDefs.getComponentTypes());
//		new LEMSSemanticAnalyser(nmlDoc).analyse();
	}

	@Test
	public void testDoMoGen() {
		// The compiler will have a "domain specific library" backend
		ST stTest = merge(domainDefs, "fakeNML");
		System.out.println(stTest.render());
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
