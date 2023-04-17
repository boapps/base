package hu.bme.mit.train.controller;

import com.google.common.collect.HashBasedTable; 
import com.google.common.collect.Table; 
import hu.bme.mit.train.interfaces.TrainController;
import java.util.Date;

public class TrainControllerImpl implements TrainController {

	Table<Date, Integer, Integer> logTable = HashBasedTable.create();
	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean speedBoostActive;

	public TrainControllerImpl() {
		Thread thread = new Thread(){
			public void run() {
			while(true) {
				try {
					followSpeed();
					Thread.sleep(2000);
				} catch (InterruptedException error){

				}
			}
		}
		};
	}

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			if(referenceSpeed+step > 0) {
				referenceSpeed += step;
			} else {
				referenceSpeed = 0;
			}
		}

		if (speedBoostActive) {
			referenceSpeed = speedLimit;
		} else {
			enforceSpeedLimit();
		}

		logTable.put(new Date(), this.step, referenceSpeed);
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
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

	@Override
	public void setSpeedBoostActive(boolean speedBoostActive ) {
		this.speedBoostActive = speedBoostActive ;		
	}

	@Override
	public Table<Date, Integer, Integer> getLogTable() {
		return logTable;
	}

}
