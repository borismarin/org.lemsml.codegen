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
	protected List<Base> bases = new ArrayList<Base>();
	@XmlElement(name = "Foo")
	protected List<Foo> foos = new ArrayList<Foo>();
	@XmlElement(name = "Bar")
	protected List<Bar> bars = new ArrayList<Bar>();
	@XmlElement(name = "Baz")
	protected List<Baz> bazs = new ArrayList<Baz>();
	@XmlElement(name = "Goo")
	protected List<Goo> goos = new ArrayList<Goo>();

	@XmlElement(name = "Components")
	protected List<Component> components;

	public List<Foo> getFoos() {
		return this.foos;
	}

	public List<Bar> getBars() {
		return this.bars;
	}

	public List<Baz> getBazs() {
		return this.bazs;
	}

	public List<Base> getBases() {
		return this.bases;
	}

	public List<Goo> getGoos() {
		return this.goos;
	}

	public <T extends Component> List<T> getAllOfType(Class<T> type) {
		return getAllOfType(type, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getAllOfType(Class<T> type, HasComponents node) {
		List<T> accum = new ArrayList<T>();
		for(Component c : node.getComponents()){
			accum.addAll(getAllOfType(type, (HasComponents) c));
		}
		if(type.isInstance(node)){
			accum.add((T) node);
		}
		return accum;
	}


	@Override
	public List<Component> getComponents() {
		List<Component> comps = new ArrayList<Component>();
		if (components == null) {
			for (Base b : getBases()) {
				b.setType(b.getClass().getSimpleName());
				comps.add(b);
			}
			for (Foo f : getFoos()) {
				f.setType(f.getClass().getSimpleName());
				comps.add(f);
			}
			for (Bar b : getBars()) {
				b.setType(b.getClass().getSimpleName());
				comps.add(b);
			}
			for (Baz b : getBazs()) {
				b.setType(b.getClass().getSimpleName());
				comps.add(b);
			}
			for (Goo g : getGoos()) {
				g.setType(g.getClass().getSimpleName());
				comps.add(g);
			}
		} else
			comps = this.components;
		return comps;
	}

}
