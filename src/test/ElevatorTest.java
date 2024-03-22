package test;

import common.ElevatorStatus;
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
        elevator = new Elevator(20, address, 5007, 1);
        elevator.getStatus().setFloor(16);
        elevator.getStatus().setDirection(ElevatorStatus.Direction.STATIONARY);
    }

    @Test
    void motorRunningState(){
        ElevatorState ElevatorMotorRunningState = new ElevatorRequestReceivedState();
        elevator.setCurrentState(ElevatorMotorRunningState);
        assertInstanceOf(ElevatorRequestReceivedState.class, elevator.getCurrentState());
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
    void GetStatusTest(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1);
        ElevatorStatus status = new ElevatorStatus(1, 1, ElevatorStatus.Direction.STATIONARY);
        assertEquals(status, elevator.getStatus());
    }
    @Test
    void ShouldStopTest(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1);
        elevator.add(16);
        assertFalse(elevator.shouldStop());

    }
    @Test
    void updateRequestsTest(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1);
        elevator.add(16);
        assertEquals(16, elevator.updateRequests());
    }
    @Test
    void GetCurrentRequestTest(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1);
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(16, elevator.getCurrentRequest());
    }
    @Test
    void GetNumRequestsTest(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1);
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(3, elevator.getNumRequests());
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