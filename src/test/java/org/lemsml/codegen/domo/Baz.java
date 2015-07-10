package org.lemsml.codegen.domo;

import javax.xml.bind.annotation.XmlType;

import org.lemsml.model.exceptions.LEMSCompilerException;

@XmlType
public class Baz extends Bar {

	public String getPBaz() {
		return getParameterValue("pBaz");
	}

	public void setPBaz(String val) throws LEMSCompilerException {
		withParameterValue("pBaz", val);
	}

}
