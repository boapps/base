package hu.bme.mit.train.system;

import java.lang.Thread;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingSpeedBoostActive_IncreasesReferenceSpeedToMax() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());

		user.overrideSpeedBoostActive(true);

		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void DeactivatingSpeedBoostActive_AllowsReferenceSpeedToDecrease() {
		user.overrideJoystickPosition(0);
		sensor.overrideSpeedLimit(10);
		Assert.assertEquals(0, controller.getReferenceSpeed());

		user.overrideSpeedBoostActive(true);
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());

		user.overrideSpeedBoostActive(false);
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertNotEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void DeactivatingSpeedBoostActive_AllowsReferenceSpeedToDecreaseAgain() {
		user.overrideJoystickPosition(0);
		sensor.overrideSpeedLimit(10);
		Assert.assertEquals(0, controller.getReferenceSpeed());

		user.overrideSpeedBoostActive(true);
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());

		user.overrideSpeedBoostActive(false);
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertNotEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void LogTable_ItemsAdded() {
		Assert.assertEquals(0, controller.getLogTable().size());
		user.overrideJoystickPosition(3);
		sensor.overrideSpeedLimit(10);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		controller.followSpeed();
		Assert.assertEquals(1, controller.getLogTable().size());
        	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		controller.followSpeed();
		Assert.assertEquals(2, controller.getLogTable().size());
	}
	
}
