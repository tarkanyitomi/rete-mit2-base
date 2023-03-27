package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import hu.bme.mit.train.interfaces.Tachograph;
import com.google.common.collect.Table;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	Tachograph tacho;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tacho = system.getTacho();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		Assert.assertEquals(1, controller.getDirection());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());

		user.overrideJoystickPosition(-5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_switchDirection() {
		Assert.assertEquals(0, controller.getReferenceSpeed());
		Assert.assertEquals(1, controller.getDirection());

		user.overrideJoystickPosition(-5);
		
		controller.followSpeed();
		Assert.assertEquals(-1, controller.getDirection());
		Assert.assertEquals(0, controller.getReferenceSpeed());

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
	}

	@Test
	public void BackwardSpeedLimit() {
		sensor.overrideSpeedLimit(10);

		user.overrideJoystickPosition(0);
		controller.followSpeed();

		Assert.assertEquals(0, controller.getReferenceSpeed());
		Assert.assertEquals(1, controller.getDirection());

		user.overrideJoystickPosition(-5);
		controller.followSpeed();

		Assert.assertEquals(0, controller.getReferenceSpeed());
		Assert.assertEquals(-1, controller.getDirection());
		
		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());

		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void recordDataTest() {
		sensor.overrideSpeedLimit(10);
		tacho.recordData();

		Assert.assertEquals(1, tacho.getData().size());
		
		user.overrideJoystickPosition(5);
		tacho.recordData();
		Assert.assertEquals(2, tacho.getData().size());
	}
}
