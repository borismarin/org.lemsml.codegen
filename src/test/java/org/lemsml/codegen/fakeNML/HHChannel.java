package org.lemsml.codegen.fakeNML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class HHChannel extends Component {

	@XmlElement(name = "HHGate")
	private List<HHGate> gates;


	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	for (HHGate child : gates) {
        		child.setType(child.getClass().getSimpleName());
        		comps.add(child);
        	}
        } else
        	comps =  this.component;
        return comps;
	}
	public String getConductance() {
		return getParameterValue("conductance");
	}

	public void setConductance(String val) throws LEMSCompilerException {
		withParameterValue("conductance", val);
	}

	public List<HHGate> getGates() {
		return this.gates;
	}
}