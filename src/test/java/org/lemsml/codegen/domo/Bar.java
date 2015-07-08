package org.lemsml.codegen.domo;

import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType
public class Bar extends Base {
	public String getPBar() {
		return getOtherAttributes().get(new QName("pBar"));
	}
}
