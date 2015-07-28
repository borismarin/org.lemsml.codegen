package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;

@XmlType
public class iaf3 extends iaf1 {

	public String getLeakConductance() {
		return getParameterValue("leakConductance");
	}

	public void setLeakConductance(String val) throws LEMSCompilerException {
		withParameterValue("leakConductance", val);
	}

	public String getLeakReversal() {
		return getParameterValue("leakReversal");
	}

	public void setLeakReversal(String val) throws LEMSCompilerException {
		withParameterValue("leakReversal", val);
	}

	public String getDeltaV() {
		return getParameterValue("deltaV");
	}

	public void setDeltaV(String val) throws LEMSCompilerException {
		withParameterValue("deltaV", val);
	}

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