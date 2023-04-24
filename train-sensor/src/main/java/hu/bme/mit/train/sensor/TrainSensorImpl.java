package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainSensorImpl implements TrainSensor {

	private TrainController controller;
	private TrainUser user;
	private int speedLimit = 5;

	private final int lowestLimit = 0;
	private final int highestLimit = 500;
	private final double warnRatio = 0.5;

	public TrainSensorImpl(TrainController controller, TrainUser user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public int getSpeedLimit() {
		return speedLimit;
	}

	@Override
	public void overrideSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		controller.setSpeedLimit(speedLimit);

		if (speedLimit < this.lowestLimit || speedLimit > this.highestLimit || (controller.getReferenceSpeed() - speedLimit) > warnRatio * controller.getReferenceSpeed()) {
			this.user.setAlarmState(true);
		} else {
			this.user.setAlarmState(false);
		}
	}
}