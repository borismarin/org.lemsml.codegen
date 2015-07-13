package org.lemsml.codegen.domo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.interfaces.HasComponents;

@XmlRootElement(name = "FooML")
public class FooML extends org.lemsml.model.extended.Lems {

	@XmlElement(name = "Base")
	private List<Base> bases = new ArrayList<Base>();

	@XmlElement(name = "Bar")
	private List<Bar> bars = new ArrayList<Bar>();

	@XmlElement(name = "Baz")
	private List<Baz> bazs = new ArrayList<Baz>();

	@XmlElement(name = "Gar")
	private List<Gar> gars = new ArrayList<Gar>();

	@XmlElement(name = "Foo")
	private List<Foo> foos = new ArrayList<Foo>();

	@XmlElement(name = "Goo")
	private List<Goo> goos = new ArrayList<Goo>();

	@XmlElement(name = "Components")
	protected List<Component> components;

	@Override
	public List<Component> getComponents() {
	       	List<Component> comps = new ArrayList<Component>();
	       	if (components == null) {
				for (Base c : getBases()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (Bar c : getBars()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (Baz c : getBazs()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (Gar c : getGars()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (Foo c : getFoos()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (Goo c : getGoos()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
	       	} else {
	       		comps =  this.components;
	       	}
	       	return comps;
	}

	public <T extends Component> List<T> getAllOfType(Class<T> type) {
		return getAllOfType(type, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getAllOfType(Class<T> type,
	        HasComponents node) {
		List<T> accum = new ArrayList<T>();
		for (Component c : node.getComponents()) {
	        accum.addAll(getAllOfType(type, c));
		}
		if (type.isInstance(node)) {
	        accum.add((T) node);
		}
		return accum;
	}


	public List<Base> getBases() {
		return this.bases;
	}
	public List<Bar> getBars() {
		return this.bars;
	}
	public List<Baz> getBazs() {
		return this.bazs;
	}
	public List<Gar> getGars() {
		return this.gars;
	}
	public List<Foo> getFoos() {
		return this.foos;
	}
	public List<Goo> getGoos() {
		return this.goos;
	}


}