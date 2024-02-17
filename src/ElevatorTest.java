import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    Elevator elevator;
    @BeforeEach
    void setUp() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007);
    }
    @Test
    void motorRunningState(){
        ElevatorState ElevatorMotorRunningState = new ElevatorMotorRunningState();
        elevator.setCurrentState(ElevatorMotorRunningState);
        assertEquals(elevator.getCurrentState(), ElevatorMotorRunningState);
    }
    @Test
    void DestinationReachedState(){
        ElevatorState ElevatorDestinationReachedState = new ElevatorDestinationReachedState();
        elevator.setCurrentState(ElevatorDestinationReachedState);
        assertEquals(elevator.getCurrentState(), ElevatorDestinationReachedState);
    }
    @Test
    void EstablishConnectionState(){
        ElevatorState ElevatorEstablishingConnectionState = new ElevatorEstablishingConnectionState();
        elevator.setCurrentState(ElevatorEstablishingConnectionState);
        assertEquals(elevator.getCurrentState(), ElevatorEstablishingConnectionState);
    }
    @Test
    void IdleState(){
        ElevatorState ElevatorIdleState = new ElevatorIdleState();
        elevator.setCurrentState(ElevatorIdleState);
        assertEquals(elevator.getCurrentState(), ElevatorIdleState);
    }
    @Test
    void TaskReceivedState(){
        ElevatorState ElevatorTaskReceivedState = new ElevatorTaskReceivedState();
        elevator.setCurrentState(ElevatorTaskReceivedState);
        assertEquals(elevator.getCurrentState(), ElevatorTaskReceivedState);
    }

    //None of the following methods have been implemented thus these can not be tested
    @Test
    void testNumButtons () {
            //assertEquals(elevator.getNumButtons(), 20);
        }
    @Test
    void testNumLamps () {
        //assertEquals(elevator.getNumButtons(), 20);
    }
    @Test
    void doorClosed () {
        //assertTrue(elevator.getCurrentState().equals("ElevatorMotorRunningState"));
    }
    @Test
    void doorOpen () {
        //assertTrue(elevator.getCurrentState() != ElevatorMotorRunningState);
    }
}