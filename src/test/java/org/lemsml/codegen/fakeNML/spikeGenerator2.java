package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;

@XmlType
public class spikeGenerator2 extends spikeGenerator {

	public String getPeriod() {
		return getParameterValue("period");
	}

	public void setPeriod(String val) throws LEMSCompilerException {
		withParameterValue("period", val);
	}
}

