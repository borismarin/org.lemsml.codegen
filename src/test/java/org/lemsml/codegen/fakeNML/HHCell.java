package org.lemsml.codegen.fakeNML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class HHCell extends Component {

	@XmlElement(name = "ChannelPopulation")
	private List<ChannelPopulation> populations;

	@Override
	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	for (ChannelPopulation child : populations) {
        		child.setType(child.getClass().getSimpleName());
        		comps.add(child);
        	}
        } else
        	comps =  this.component;
        return comps;
	}

	public String getCapacitance() {
		return getParameterValue("capacitance");
	}

	public void setCapacitance(String val) throws LEMSCompilerException {
		withParameterValue("capacitance", val);
	}

	public String getInjection() {
		return getParameterValue("injection");
	}

	public void setInjection(String val) throws LEMSCompilerException {
		withParameterValue("injection", val);
	}

	public String getV0() {
		return getParameterValue("v0");
	}

	public void setV0(String val) throws LEMSCompilerException {
		withParameterValue("v0", val);
	}

	public List<ChannelPopulation> getPopulations() {
		return this.populations;
	}
}