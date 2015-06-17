package org.lemsml.codegen;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.extended.Lems;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class schemaGenTest
{

	protected File getLocalFile(String fname) {
		return new File(getClass().getResource(fname).getFile());
	}

	private File hindMarshRoseSimFile;
	private Lems lems;

	@Before
	public void setUp() throws Throwable
	{
		hindMarshRoseSimFile = getLocalFile("/examples/nml/Run_Chaotic_HindmarshRose.xml");
		LEMSCompilerFrontend compiler = new LEMSCompilerFrontend(hindMarshRoseSimFile);
		lems = compiler.generateLEMSDocument();
	}


	@Test
	public void test()
	{
		URL stURL = getClass().getResource("/stringtemplate/dsl_xsd.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		group.registerRenderer(String.class, new StringRenderer());
		ST stTest = group.getInstanceOf("dsl_xsd");

		stTest.add("lems", this.lems);

		System.out.println(stTest.render());

	}

}
