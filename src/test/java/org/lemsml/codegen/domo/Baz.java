package org.lemsml.codegen.domo;

import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType
public class Baz extends Bar {
	public String getPBaz() {
		return getOtherAttributes().get(new QName("pBaz"));
	}
}
