package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainController;


public class TrainSensorTest {
    TrainController mockTrainController;
    TrainUser mockTrainUser;
    TrainSensorImpl trainSensorIml;

    @Before
    public void before() {
        mockTrainUser = mock(TrainUser.class);
        mockTrainController = mock(TrainController.class);
        trainSensorIml = new TrainSensorImpl(mockTrainController, mockTrainUser);
    }

    @Test
    public void TestAbsoluteBelow() {
        trainSensorIml.overrideSpeedLimit(-10);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void TestAbsoluteAbove() {
        trainSensorIml.overrideSpeedLimit(1000);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void TestAbsoluteCorrect() {
        trainSensorIml.overrideSpeedLimit(50);
        verify(mockTrainUser, times(0)).setAlarmState(true);
    }

    @Test
    public void TestRelativeBelow() {
        trainSensorIml.overrideSpeedLimit(-50);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }
}
