package test;

import common.Direction;
import common.ElevatorStatus;
import elevator.*;
import org.junit.jupiter.api.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

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

    /**
     * Test if the elevator is in motorRunningState when called
     */
    @Test
    void motorRunningState(){
        ElevatorState ElevatorMotorRunningState = new ElevatorRequestReceivedState();
        elevator.setCurrentState(ElevatorMotorRunningState);
        assertInstanceOf(ElevatorRequestReceivedState.class, elevator.getCurrentState());
    }

    /**
     * Test if the elevator is in EstablishConnectionState when called
     */
    @Test
    void EstablishConnectionState(){
        ElevatorState ElevatorEstablishingConnectionState = new ElevatorEstablishingConnectionState();
        elevator.setCurrentState(ElevatorEstablishingConnectionState);
        assertInstanceOf(ElevatorEstablishingConnectionState.class, elevator.getCurrentState());
    }

    /**
     * Test if the elevator is in IdleState when called
     */
    @Test
    void IdleState(){
        ElevatorState ElevatorIdleState = new ElevatorIdleState();
        elevator.setCurrentState(ElevatorIdleState);
        assertInstanceOf(elevator.ElevatorIdleState.class, elevator.getCurrentState());
    }

    /**
     * Test getStatus method
     */
    @Test
    void GetStatusTest(){
        ElevatorStatus status = new ElevatorStatus(1, 1, Direction.STATIONARY, false);
        assertEquals(status, elevator.getStatus());
    }

    /**
     * Test shouldStop method
     */
    @Test
    void ShouldStopTest(){
        elevator.add(16);
        assertFalse(elevator.shouldStop());
    }

    /**
     * Test updateRequests method
     */
    @Test
    void updateRequestsTest(){
        elevator.add(16);
        assertEquals(16, elevator.updateRequests());
    }

    /**
     * Test getCurrentRequest method
     */
    @Test
    void GetCurrentRequestTest(){
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(16, elevator.getCurrentRequest());
    }

    /**
     * Test getNumRequests method
     */
    @Test
    void GetNumRequestsTest(){
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(3, elevator.getNumRequests());
    }

    /**
     * Test getDoorStatus method
     */
    @Test
    void getDoorStatusTest(){
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
    }

    /**
     * Test openDoor method
     */
    @Test
    void openDoorTest () {
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
    }

    /**
     * Test forceDoorClose method
     */
    @Test
    void forceCloseDoorTest(){
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
        elevator.forceCloseDoor();
        assertFalse(elevator.getDoorStatus());
    }

    /**
     * Test closeDoor method
     */
    @Test
    void closeDoorTest(){
        assertFalse(elevator.getDoorStatus());
        elevator.openDoor();
        assertTrue(elevator.getDoorStatus());
        try {
            elevator.closeDoor(3, new Random());
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

    /**
     * Test getTimerStatus method
     */
    @Test
    void getTimerStatusTest(){
        assertNull(elevator.getTimerStatus());
    }

    /**
     * Test startTimer method
     */
    @Test
    void startTimerTest(){
        elevator.startTimer(3);
        assertNotNull(elevator.getTimerStatus());
        assertTrue(elevator.getTimerStatus().isAlive());
        assertFalse(elevator.getTimerStatus().isInterrupted());
        assertEquals("Timer: " + 3 + " seconds", elevator.getTimerStatus().getName());
    }

    /**
     * Test stopTimer method
     */
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

    /**
     * Test getMotorStatus method
     */
    @Test
    void getMotorStatusTest(){
        assertNull(elevator.getMotorStatus());
    }

    /**
     * Test startMotor method
     */
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

    /**
     * Test timeout method
     */
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

    /**
     * Test addPassenger method
     */
    @Test
    void addPassengerTest(){
        assertTrue(elevator.getStatus().isEmpty());
        elevator.addPassenger();
        assertFalse(elevator.getStatus().isEmpty());
    }

    /**
     * Test removePassenger method
     */
    @Test
    void removePassengerTest(){
        assertTrue(elevator.getStatus().isEmpty());
        elevator.addPassenger();
        assertFalse(elevator.getStatus().isEmpty());
        elevator.removePassenger();
        assertTrue(elevator.getStatus().isEmpty());
    }

    /**
     * Test add method
     */
    @Test
    void addTest(){
        //ensure floor doesnt get added when > numFloors
        try {
            elevator.add(40);
        }
        catch(IndexOutOfBoundsException ignored){}
        //ensure floor doesnt get added when < 0
        try {
            elevator.add(-5);
        }
        catch(IndexOutOfBoundsException ignored){}
        //ensure floor doesnt get added when == 0
        try {
            elevator.add(0);
        }
        catch(IndexOutOfBoundsException ignored){}
        // Regular cases
        elevator.add(16);
        elevator.add(8);
        elevator.add(3);
        assertEquals(16, elevator.getCurrentRequest());
        assertEquals(3, elevator.getNumRequests());
    }
}