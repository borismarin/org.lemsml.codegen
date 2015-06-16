package org.lemsml.codegen;

import java.util.HashMap;

import org.lemsml.model.exceptions.LEMSCompilerError;
import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Component;
import org.lemsml.model.extended.ComponentType;
import org.lemsml.model.extended.Lems;
import org.lemsml.visitors.BaseVisitor;

public class DSLSchemaBuilder extends BaseVisitor<Void, Throwable> {

	private Lems lems;
	private HashMap<String, ComponentType> compTypes;

	public DSLSchemaBuilder(Lems lems) throws Throwable {
		this.lems = lems;
		this.compTypes = new HashMap<String, ComponentType>();
	}

	@Override
	public Void visit(Component comp) throws Throwable {
		ComponentType typeToSet = lems.getComponentTypeByName(comp.getType());
		if (null == typeToSet) {
			throw new LEMSCompilerException(
					"Trying to build Component of unknow type "
							+ comp.getType(),
					LEMSCompilerError.ComponentTypeNotDefined);
		}
		comp.setComponentType(typeToSet);
		return null;
	}

}
