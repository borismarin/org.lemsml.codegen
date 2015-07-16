package org.lemsml.codegen.domo;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
	}

	public Foo createFoo() {
		return new Foo();
	}

	public Goo createGoo() {
		return new Goo();
	}

	public Gar createGar() {
		return new Gar();
	}

	public Bar createBar() {
		return new Bar();
	}

	public Bar createBaz() {
		return new Baz();
	}

	public Base createBase() {
		return new Base();
	}

	public FooML createFooML() {
		return new FooML();
	}


}
