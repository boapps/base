package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private boolean speedBoostActive;
	private boolean alarmState;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmState() {
		return alarmState;
	}

	@Override
	public void setAlarmState(boolean alarmState) {
		this.alarmState = alarmState;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public boolean getSpeedBoostActive() {
		return speedBoostActive;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);
	}

	@Override
	public void overrideSpeedBoostActive(boolean speedBoostActive) {
		this.speedBoostActive = speedBoostActive;
		controller.setSpeedBoostActive(speedBoostActive);
	}

}
