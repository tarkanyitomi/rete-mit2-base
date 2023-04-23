package hu.bme.mit.train.tacho;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.Tachograph;

public class TachoImpl implements Tachograph {

	private TrainController controller;
	private TrainUser user;
	private com.google.common.collect.Table<java.util.Date, Integer, Integer> store = com.google.common.collect.HashBasedTable.create();

	public TachoImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public void recordData() {
		store.put(new java.util.Date(), controller.getReferenceSpeed(), user.getJoystickPosition());
	}

	@Override
	public com.google.common.collect.Table<java.util.Date, Integer, Integer> getData() {
		return store;
	}
}