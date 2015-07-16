package org.lemsml.codegen.fakeNML;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
	}

	public FakeNML createFakeNML() {
		return new FakeNML();
	}

	public HHCell createHHCell() {
		return new HHCell();
	}

	public HHChannel createHHChannel() {
		return new HHChannel();
	}

	public HHExpLinearRate createHHExpLinearRate() {
		return new HHExpLinearRate();
	}

	public HHExpRate createHHExpRate() {
		return new HHExpRate();
	}

	public HHGate createHHGate() {
		return new HHGate();
	}

	public HHGate0 createHHGate0() {
		return new HHGate0();
	}

	public HHRate createHHRate() {
		return new HHRate();
	}

	public HHSigmoidRate createHHSigmoidRate() {
		return new HHSigmoidRate();
	}

	public iaf1 createiaf1() {
		return new iaf1();
	}

	public iaf2 createiaf2() {
		return new iaf2();
	}

	public iaf3 createiaf3() {
		return new iaf3();
	}



	public ChannelPopulation createChannelPopulation() {
		return new ChannelPopulation();
	}




}
