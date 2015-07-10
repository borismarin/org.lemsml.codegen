package org.lemsml.codegen.domo;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Bar extends Base {
	public String getPBar() {
		return getParameterValue("pBar");
	}
}
