package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private int direction = 1;
	private Thread thread;

	public TrainControllerImpl() {
		thread = new Thread(){
			public void run(){
				thread.run();
				try{
					followSpeed();
					thread.sleep(1000);
				} catch(InterruptedException ie){
					ie.printStackTrace();
				}
			}
		};
	}

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
	}
}
