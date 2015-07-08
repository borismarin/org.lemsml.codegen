package org.lemsml.codegen;

import org.lemsml.model.exceptions.LEMSCompilerError;
import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.ComponentType;
import org.lemsml.model.extended.Lems;
import org.lemsml.visitors.BaseVisitor;

public class DSLSchemaBuilder extends BaseVisitor<Void, Throwable> {

	private Lems lems;

	public DSLSchemaBuilder(Lems lems) throws Throwable {
		this.lems = lems;
	}

	@Override
	public Void visit(Component comp) throws Throwable {
		ComponentType typeToSet = lems.getComponentTypeByName(comp.getType());
		if (null == typeToSet) {
			throw new LEMSCompilerException(
					"Trying to build Component of unknow type "
							+ comp.getType(),
					LEMSCompilerError.UndefinedComponentType);
		}
		comp.setComponentType(typeToSet);
		return null;
	}

}
