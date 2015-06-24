package org.lemsml.codegen.domo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.lemsml.model.extended.Component;

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

	public List<Bar> getAllBars() {

		List<Bar> bars = new ArrayList<Bar>();
		bars.addAll(getBars());
		bars.addAll(getBazs());
		return bars;
	}

	public List<Base> getAllBases() {
		List<Base> bases = new ArrayList<Base>();
		bases.addAll(getBases());
		bases.addAll(getFoos());
		bases.addAll(getBars());
		bases.addAll(getBazs());
		return bases;
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
		} else
			comps = this.components;
		return comps;
	}

}
