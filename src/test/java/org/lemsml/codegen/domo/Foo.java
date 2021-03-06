package org.lemsml.codegen.domo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class Foo extends Base {

	@XmlElement(name = "Bar")
	private Bar fooBar;

	@XmlElement(name = "Baz")
	private List<Baz> fooBazs;


	@Override
	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	for (Baz child : fooBazs) {
        		child.setType(child.getClass().getSimpleName());
        		comps.add(child);
        	}
        	fooBar.setType(fooBar.getClass().getSimpleName());
            comps.add(fooBar);
        } else
        	comps =  this.component;
        return comps;
	}
	public String getPFoo() {
		return getParameterValue("pFoo");
	}

	public void setPFoo(String val) throws LEMSCompilerException {
		withParameterValue("pFoo", val);
	}

	public Bar getFooBar() {
		return this.fooBar;
	}

	public List<Baz> getFooBazs() {
		return this.fooBazs;
	}
}
