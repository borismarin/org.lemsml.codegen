package org.lemsml.codegen;

import java.util.Locale;

import org.stringtemplate.v4.AttributeRenderer;

public class ExpressionRenderer implements AttributeRenderer{
	 public String toString(Object o, String formatString, Locale locale) {
	        String s = (String)o;
	        if ( formatString==null ) return s;
	        if ( formatString.equals("upper") ) return s.toUpperCase(locale);
	        if ( formatString.equals("lower") ) return s.toLowerCase(locale);
			return s;
	 }
}

