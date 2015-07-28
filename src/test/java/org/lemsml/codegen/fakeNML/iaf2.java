package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;

@XmlType
public class iaf2 extends iaf1 {

	public String getThreshold() {
		return getParameterValue("threshold");
	}

	public void setThreshold(String val) throws LEMSCompilerException {
		withParameterValue("threshold", val);
	}

	public String getRefractoryPeriod() {
		return getParameterValue("refractoryPeriod");
	}

	public void setRefractoryPeriod(String val) throws LEMSCompilerException {
		withParameterValue("refractoryPeriod", val);
	}

	public String getCapacitance() {
		return getParameterValue("capacitance");
	}

	public void setCapacitance(String val) throws LEMSCompilerException {
		withParameterValue("capacitance", val);
	}
}