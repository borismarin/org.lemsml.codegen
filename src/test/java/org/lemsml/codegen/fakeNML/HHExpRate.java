package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;

@XmlType
public class HHExpRate extends HHRate {

	public String getRate() {
		return getParameterValue("rate");
	}

	public void setRate(String val) throws LEMSCompilerException {
		withParameterValue("rate", val);
	}

	public String getMidpoint() {
		return getParameterValue("midpoint");
	}

	public void setMidpoint(String val) throws LEMSCompilerException {
		withParameterValue("midpoint", val);
	}

	public String getScale() {
		return getParameterValue("scale");
	}

	public void setScale(String val) throws LEMSCompilerException {
		withParameterValue("scale", val);
	}
}