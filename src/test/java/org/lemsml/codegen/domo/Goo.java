package org.lemsml.codegen.domo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.extended.Component;

@XmlType
public class Goo extends Base {

	@XmlElement(name = "Baz")
	private List<Baz> gooBazs;

	@XmlElement(name = "Gar")
	private List<Gar> gooGars;

	@Override
	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	for (Baz child : gooBazs) {
        		child.setType(child.getClass().getSimpleName());
        		comps.add(child);
        	}
        	for (Gar child : gooGars) {
        		child.setType(child.getClass().getSimpleName());
        		comps.add(child);
        	}
        } else
        	comps =  this.component;
        return comps;
	}

	public List<Baz> getGooBazs() {
		return this.gooBazs;
	}

	public List<Gar> getGooGars() {
		return this.gooGars;
	}
}