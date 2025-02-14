package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;
import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private int direction = 1;

	private Timer timer;

	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			followSpeed();
		}
	};

	@Override
	public void followSpeed() {
		if (referenceSpeed == 0 && dirRequest() && step != 0) {
			if (step > 0) {
				direction = 1;
			} else {
				direction = -1;
			}
		} else if (direction == 1) {
			referenceSpeed += step;
		} else {
			referenceSpeed -= step;
		}

		enforceSpeedLimit();
	}

	private Boolean dirRequest() {
		if (step == 0) {
			return false;
		}

		int stepP = step > 0 ? 1 : -1;

		return stepP != direction;
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public int getDirection() {
		return direction;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;

		if(this.timer == null) {
			this.timer = new Timer();
			this.timer.schedule(task, 200, 200);
		}

	}
}
