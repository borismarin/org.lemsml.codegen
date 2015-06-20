package org.lemsml.codegen;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.lemsml.model.Child;
import org.lemsml.model.compiler.LEMSCompilerFrontend;
import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.ComponentType;
import org.lemsml.model.extended.Lems;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class DomGen
{

	protected File getLocalFile(String fname) {
		return new File(getClass().getResource(fname).getFile());
	}



	//@Test
	public void testPend() throws Throwable
	{
		File pendFile = getLocalFile("/examples/opensourcechaos/SimplePendulum.xml");
		LEMSCompilerFrontend compiler = new LEMSCompilerFrontend(pendFile);
		Lems lems = compiler.generateLEMSDocument();

		ST stTest = merge(lems);

		System.out.println(stTest.render());

	}

	@Test
	public void testNest() throws Throwable
	{

		ComponentType Base = (ComponentType) new ComponentType()
			.withName("Base");

		ComponentType Bar = (ComponentType) new ComponentType()
			.withName("Bar")
			.withExtends("Base");

		ComponentType Foo = (ComponentType) new ComponentType()
			.withName("Foo")
			.withChildren(new Child().withType("Bar"));

		Component bar0 = (Component) new Component()
			.withType("Bar")
			.withName("bar0");

		Component foo0 = (Component) new Component()
			.withId("foo0")
			.withType("Foo")
			.withComponent(bar0);

		Lems lems = (Lems) new Lems()
			.withComponentTypes(Base, Bar, Foo)
			.withComponents(foo0);

		LEMSCompilerFrontend.semanticAnalysis(lems);

		ST stTest = merge(lems);

		System.out.println(stTest.render());

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
