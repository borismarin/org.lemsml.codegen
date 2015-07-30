package org.lemsml.codegen;

import org.lemsml.model.exceptions.LEMSCompilerException;
import org.lemsml.model.extended.Scope;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class ScopeAdaptor extends ObjectModelAdaptor {
	 public Object getProperty(Interpreter interpreter, ST self, Object o, Object property, String propertyName)
		        throws STNoSuchPropertyException
		    {
		        try {
		        	if(propertyName.equals("dependencies")){
		        	    return super.getProperty(interpreter,self,o, property,propertyName);
		        	}
					return ((Scope) o).resolve(propertyName);
				} catch (LEMSCompilerException e) {
					// TODO Auto-generated catch block
					throw new STNoSuchPropertyException(e, property, propertyName);
				}
		    }
}
