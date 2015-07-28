package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;

@XmlType
public class spikeGenerator extends Component {

	public String getPeriod() {
		return getParameterValue("period");
	}

	public void setPeriod(String val) throws LEMSCompilerException {
		withParameterValue("period", val);
	}
}
