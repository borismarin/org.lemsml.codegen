package org.lemsml.codegen.fakeNML;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.interfaces.HasComponents;

@XmlRootElement(name = "FakeNML")
public class FakeNML extends org.lemsml.model.extended.Lems {

	@XmlElement(name = "iaf1")
	private List<iaf1> iaf1s = new ArrayList<iaf1>();

	@XmlElement(name = "iaf2")
	private List<iaf2> iaf2s = new ArrayList<iaf2>();

	@XmlElement(name = "iaf3")
	private List<iaf3> iaf3s = new ArrayList<iaf3>();

	@XmlElement(name = "spikeGenerator")
	private List<spikeGenerator> spikegenerators = new ArrayList<spikeGenerator>();

	@XmlElement(name = "spikeGenerator2")
	private List<spikeGenerator2> spikegenerator2s = new ArrayList<spikeGenerator2>();

	@XmlElement(name = "HHRate")
	private List<HHRate> hhrates = new ArrayList<HHRate>();

	@XmlElement(name = "HHExpRate")
	private List<HHExpRate> hhexprates = new ArrayList<HHExpRate>();

	@XmlElement(name = "HHSigmoidRate")
	private List<HHSigmoidRate> hhsigmoidrates = new ArrayList<HHSigmoidRate>();

	@XmlElement(name = "HHExpLinearRate")
	private List<HHExpLinearRate> hhexplinearrates = new ArrayList<HHExpLinearRate>();

	@XmlElement(name = "HHGate0")
	private List<HHGate0> hhgate0s = new ArrayList<HHGate0>();

	@XmlElement(name = "HHGate")
	private List<HHGate> hhgates = new ArrayList<HHGate>();

	@XmlElement(name = "HHChannel")
	private List<HHChannel> hhchannels = new ArrayList<HHChannel>();

	@XmlElement(name = "ChannelPopulation")
	private List<ChannelPopulation> channelpopulations = new ArrayList<ChannelPopulation>();

	@XmlElement(name = "HHCell")
	private List<HHCell> hhcells = new ArrayList<HHCell>();

	@XmlElement(name = "Components")
	protected List<Component> components;

	@Override
	public List<Component> getComponents() {
	       	List<Component> comps = new ArrayList<Component>();
	       	if (components == null) {
				for (iaf1 c : getIaf1s()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (iaf2 c : getIaf2s()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (iaf3 c : getIaf3s()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (spikeGenerator c : getSpikeGenerators()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (spikeGenerator2 c : getSpikeGenerator2s()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHRate c : getHHRates()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHExpRate c : getHHExpRates()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHSigmoidRate c : getHHSigmoidRates()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHExpLinearRate c : getHHExpLinearRates()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHGate0 c : getHHGate0s()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHGate c : getHHGates()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHChannel c : getHHChannels()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (ChannelPopulation c : getChannelPopulations()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
				for (HHCell c : getHHCells()) {
					c.setType(c.getClass().getSimpleName());
					comps.add(c);
				}
	       	} else {
	       		comps =  this.components;
	       	}
	       	return comps;
	}

	public <T extends Component> Set<T> getAllOfType(Class<T> type) {
		return getAllOfType(type, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> Set<T> getAllOfType(Class<T> type,
	        HasComponents node) {
		Set<T> accum = new HashSet<T>();
		for (Component c : node.getComponents()) {
	        accum.addAll(getAllOfType(type, c));
		}
		if (type.isInstance(node)) {
	        accum.add((T) node);
		}
		return accum;
	}


	public List<iaf1> getIaf1s() {
		return this.iaf1s;
	}
	public List<iaf2> getIaf2s() {
		return this.iaf2s;
	}
	public List<iaf3> getIaf3s() {
		return this.iaf3s;
	}
	public List<spikeGenerator> getSpikeGenerators() {
		return this.spikegenerators;
	}
	public List<spikeGenerator2> getSpikeGenerator2s() {
		return this.spikegenerator2s;
	}
	public List<HHRate> getHHRates() {
		return this.hhrates;
	}
	public List<HHExpRate> getHHExpRates() {
		return this.hhexprates;
	}
	public List<HHSigmoidRate> getHHSigmoidRates() {
		return this.hhsigmoidrates;
	}
	public List<HHExpLinearRate> getHHExpLinearRates() {
		return this.hhexplinearrates;
	}
	public List<HHGate0> getHHGate0s() {
		return this.hhgate0s;
	}
	public List<HHGate> getHHGates() {
		return this.hhgates;
	}
	public List<HHChannel> getHHChannels() {
		return this.hhchannels;
	}
	public List<ChannelPopulation> getChannelPopulations() {
		return this.channelpopulations;
	}
	public List<HHCell> getHHCells() {
		return this.hhcells;
	}


}