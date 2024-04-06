package test;

import common.Direction;
import common.ElevatorStatus;
import elevator.*;
import org.junit.jupiter.api.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    static Elevator elevator;

    static InetAddress address;
    @BeforeEach
    void setUp() {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(22, address, 5007, 1);
        elevator.getStatus().setDirection(Direction.STATIONARY);
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
        ElevatorStatus status = new ElevatorStatus(1, 1, Direction.STATIONARY);
        assertEquals(status, elevator.getStatus());
    }
    @Test
    void ShouldStopTest(){
        elevator.add(16);
        assertFalse(elevator.shouldStop());

    }
    @Test
    void updateRequestsTest(){
        elevator.add(16);
        assertEquals(16, elevator.updateRequests());
    }
    @Test
    void GetCurrentRequestTest(){
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(16, elevator.getCurrentRequest());
    }
    @Test
    void GetNumRequestsTest(){
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(3, elevator.getNumRequests());
    }
    @Test
    void getDoorStatusTest(){
        assertEquals(false, elevator.getDoorStatus());
        elevator.openDoor();
        assertEquals(true, elevator.getDoorStatus());
    }
    @Test
    void openDoorTest () {
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
    }
    @Test
    void forceCloseDoorTest(){
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
        elevator.forceCloseDoor();
        assertFalse(elevator.getDoorStatus());
    }
    @Test
    void closeDoorTest(){
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
        try {
            elevator.closeDoor();
        } catch (Exception e) {
            System.out.println("Elevator door is stuck open!");
            assertTrue(elevator.getDoorStatus());
            System.out.println("Forcing door closed...");
            elevator.forceCloseDoor();
            System.out.println("Forced door closure!");
        }
        assertFalse(elevator.getDoorStatus());
    }
    //Beyond here, Testing code gets kind of weird, due to threads, testing may or may not pass.
    @Test
    void getTimerStatusTest(){
        assertNull(elevator.getTimerStatus());
    }
    @Test
    void startTimerTest(){
        elevator.startTimer(3);
        assertNotNull(elevator.getTimerStatus());
        assertTrue(elevator.getTimerStatus().isAlive());
        assertFalse(elevator.getTimerStatus().isInterrupted());
        assertEquals("Timer: " + 3 + " seconds", elevator.getTimerStatus().getName());
    }
    @Test
    void stopTimerTest(){
        elevator.startTimer(3);
        assertNotNull(elevator.getTimerStatus());
        assertTrue(elevator.getTimerStatus().isAlive());
        assertFalse(elevator.getTimerStatus().isInterrupted());
        assertEquals("Timer: " + 3 + " seconds", elevator.getTimerStatus().getName());
        elevator.stopTimer();
        assertTrue(elevator.getTimerStatus().isAlive());
        assertTrue(elevator.getTimerStatus().isInterrupted());
    }
    @Test
    void getMotorStatusTest(){
        assertNull(elevator.getMotorStatus());
    }
    @Test
    void startMotorTest(){
        elevator.add(22);
        elevator.getStatus().setDirection(Direction.UP);
        elevator.startMotor();
        assertNotNull(elevator.getMotorStatus());
        assertTrue(elevator.getMotorStatus().isAlive());
        assertFalse(elevator.getMotorStatus().isInterrupted());
        assertEquals("Motor", elevator.getMotorStatus().getName());
    }

    @Test
    void timeoutTest(){
        elevator.add(22);
        elevator.getStatus().setDirection(Direction.UP);
        elevator.startMotor();
        assertNotNull(elevator.getMotorStatus());
        assertTrue(elevator.getMotorStatus().isAlive());
        assertFalse(elevator.getMotorStatus().isInterrupted());
        assertEquals("Motor", elevator.getMotorStatus().getName());
        elevator.timeout();
        assertTrue(elevator.getMotorStatus().isAlive());
        assertTrue(elevator.getMotorStatus().isInterrupted());
    }

    // None of the following methods have been implemented thus these can not be tested
    /*
    @Test
    void testNumButtons () {

    }
    @Test
    void testNumLamps () {

    }
    */
}