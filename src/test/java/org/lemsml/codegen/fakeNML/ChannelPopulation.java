package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class ChannelPopulation extends Component {

	public String getNumber() {
		return getParameterValue("number");
	}

	public void setNumber(String val) throws LEMSCompilerException {
		withParameterValue("number", val);
	}

	public String getErev() {
		return getParameterValue("erev");
	}

	public void setErev(String val) throws LEMSCompilerException {
		withParameterValue("erev", val);
	}
}