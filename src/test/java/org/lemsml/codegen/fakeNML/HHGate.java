package org.lemsml.codegen.fakeNML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class HHGate extends Component {

	@XmlElement(name = "Forward")
	private HHRate Forward;

	@XmlElement(name = "Reverse")
	private HHRate Reverse;


	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	Forward.setType((null != Forward.getType()) ? Forward.getType() : Forward.getClass().getSimpleName());
        	Forward.setName("Forward");
            comps.add(Forward);
        	Reverse.setType((null != Reverse.getType()) ? Reverse.getType() : Reverse.getClass().getSimpleName());
        	Reverse.setName("Reverse");
            comps.add(Reverse);
        } else
        	comps =  this.component;
        return comps;
	}
	public String getPower() {
		return getParameterValue("power");
	}

	public void setPower(String val) throws LEMSCompilerException {
		withParameterValue("power", val);
	}

	public HHRate getForward() {
		return this.Forward;
	}


	public HHRate getReverse() {
		return this.Reverse;
	}
}
