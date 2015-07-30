package org.lemsml.codegen.fakeNML;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class HHGate0 extends Component {

	@XmlElement(name = "HHRate")
	private HHRate Forward;

	@XmlElement(name = "HHRate")
	private HHRate Reverse;

	@Override
	public List<Component> getComponent() {
        List<Component> comps = new ArrayList<Component>();
        if (component == null) {
        	Forward.setType(Forward.getClass().getSimpleName());
            comps.add(Forward);
        	Reverse.setType(Reverse.getClass().getSimpleName());
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