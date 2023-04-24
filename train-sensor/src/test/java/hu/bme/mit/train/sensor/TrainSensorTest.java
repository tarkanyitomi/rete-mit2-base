package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainController;

public class TrainSensorTest {

    TrainSensorImpl sensor;
    TrainUser mockTrainUser;
    TrainController mockTrainController;

    @Before
    public void before() {
        mockTrainUser = mock(TrainUser.class);
        mockTrainController = mock(TrainController.class);
        when(mockTrainController.getReferenceSpeed()).thenReturn(150);

        sensor = new TrainSensorImpl(mockTrainController, mockTrainUser);
    }

    //verifies that a speed limit beyond zero and alarms
    @Test
    public void TestNegativSpeedLimit() {
        sensor.overrideSpeedLimit(-1);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }    
    
    //verifies that speed limit is higher than the maximum and alarms
    @Test
    public void TooHighSpeedLimit() {
        sensor.overrideSpeedLimit(501);
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    //verifies that a legal speed limit does not trigger the alarm
    @Test
    public void ZeroSpeedWithLimitNotTriggers() {
        sensor.overrideSpeedLimit(130);
        verify(mockTrainUser, times(1)).setAlarmState(false);
    }


    //verifies alarm triggers when there is high speed difference
    @Test
    public void SpeedDifferenceTriggersAlarm() {
        sensor.overrideSpeedLimit(50);        
        verify(mockTrainUser, times(1)).setAlarmState(true);
    }

    @Test
    public void testSpeedLimitSaved(){
        sensor.overrideSpeedLimit(42);
        Assert.assertEquals(42, sensor.getSpeedLimit());
    }
}
