package org.lemsml.codegen;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.lemsml.model.ComponentType;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.extended.Lems;

public class HindMarshRoseTest
{
	protected File getLocalFile(String fname) {
		return new File(getClass().getResource(fname).getFile());
	}

	/**
	 * @author borismarin
	 *
	 */

	private File hindMarshRoseSimFile;

	@Before
	public void setUp()
	{
		hindMarshRoseSimFile = getLocalFile("/examples/nml/Run_Chaotic_HindmarshRose.xml");
	}

	@Test
	public void testParsing() throws Throwable
	{

		LEMSCompilerFrontend compiler = new LEMSCompilerFrontend(hindMarshRoseSimFile);

		Lems lemsDoc = compiler.generateLEMSDocument();

		ComponentType hindRoseCompType = lemsDoc.getComponentTypeByName("hindmarshRoseCell");
		System.out.println(hindRoseCompType.getDescription());

	}

}
