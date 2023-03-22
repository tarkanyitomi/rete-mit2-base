package hu.bme.mit.train.interfaces;

public interface TrainController {

	void followSpeed();

	int getReferenceSpeed();

	int getDirection();

	void setSpeedLimit(int speedLimit);

	void setJoystickPosition(int joystickPosition);

}
