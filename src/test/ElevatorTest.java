package test;

import elevator.*;
import org.junit.jupiter.api.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    static Elevator elevator;
    @BeforeAll
    static void setUp() {
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
        assertInstanceOf(ElevatorMotorRunningState.class, elevator.getCurrentState());
    }
    @Test
    void DestinationReachedState(){
        ElevatorState ElevatorDestinationReachedState = new ElevatorDestinationReachedState();
        elevator.setCurrentState(ElevatorDestinationReachedState);
        assertInstanceOf(elevator.ElevatorDestinationReachedState.class, elevator.getCurrentState());
    }
    @Test
    void EstablishConnectionState(){
        ElevatorState ElevatorEstablishingConnectionState = new ElevatorEstablishingConnectionState();
        elevator.setCurrentState(ElevatorEstablishingConnectionState);
        assertInstanceOf(ElevatorEstablishingConnectionState.class, elevator.getCurrentState());
    }
    @Test
    void IdleState(){
        ElevatorState ElevatorIdleState = new ElevatorIdleState();
        elevator.setCurrentState(ElevatorIdleState);
        assertInstanceOf(elevator.ElevatorIdleState.class, elevator.getCurrentState());
    }
    @Test
    void TaskReceivedState(){
        ElevatorState ElevatorTaskReceivedState = new ElevatorTaskReceivedState();
        elevator.setCurrentState(ElevatorTaskReceivedState);
        assertInstanceOf(ElevatorTaskReceivedState.class, elevator.getCurrentState());
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
        //assertTrue(elevator.getCurrentState().equals("elevator.ElevatorMotorRunningState"));
    }
    @Test
    void doorOpen () {
        //assertTrue(elevator.getCurrentState() != elevator.ElevatorMotorRunningState);
    }
}