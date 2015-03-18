package org.lemsml.codegen;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class StringTemplateTest
{

	class fakeComponent
	{
		Map<String, String> parameters = new HashMap<String, String>();
		String name;

		public Map<String, String> getParameters()
		{
			return parameters;
		}

		public void setParameters(Map<String, String> parameters)
		{
			this.parameters = parameters;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public fakeComponent()
		{
			name = "fakeComp";
			parameters.put("p1", "0.1");
			parameters.put("p2", "0.2");
		}

	};

	@Test
	public void testST()
	{
		ST hello = new ST("Hello, <name>");
		hello.add("name", "World");
		System.out.println(hello.render());

	}

	@Test
	public void testGroup()
	{
		URL stURL = getClass().getResource("/stringtemplate/test.stg");
		STGroup group = new STGroupFile(stURL.getFile());
		ST stTest = group.getInstanceOf("test");

		stTest.add("dLEMS", new fakeComponent());

		System.out.println(stTest.render());

	}

}
