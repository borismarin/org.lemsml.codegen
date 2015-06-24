package org.lemsml.codegen.domo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.extended.Component;

@XmlType
public class Foo extends Base {
	@XmlElement(name = "Bar")
    protected List<Bar> bars;


	public List<Component> getComponent() {
		List<Component> comps = new ArrayList<Component>();
		if (component == null) {
			for (Bar b : bars) {
				b.setType(b.getClass().getSimpleName());
				comps.add(b);
			}
		} else
			comps =  this.component;
		return comps;
	}

}
