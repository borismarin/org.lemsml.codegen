package org.lemsml.codegen;

import java.util.Locale;

import org.lemsml.model.extended.Symbol;
import org.stringtemplate.v4.AttributeRenderer;

import expr_parser.visitors.ARenderAs;
import expr_parser.visitors.AntlrExpressionParser;
import expr_parser.visitors.RenderC;
import expr_parser.visitors.RenderJava;
import expr_parser.visitors.RenderLatex;

public class LemsExpressionRenderer implements AttributeRenderer {
	@Override
	public String toString(Object o, String formatString, Locale locale) {
		String s = (String) ((Symbol) o).getValueDefinition();
		String ret = s;
		if (null != formatString) {
			if (formatString.equals("latex"))
				ret = adaptTo(s, new RenderLatex());
			if (formatString.equals("C"))
				ret = adaptTo(s, new RenderC());
			if (formatString.equals("Java"))
				ret = adaptTo(s, new RenderJava());
		}
		return ret;
	}

	private String adaptTo(String expression, ARenderAs adaptor) {
		AntlrExpressionParser p = new AntlrExpressionParser(expression);
		return p.parseAndVisitWith(adaptor);
	}

}
