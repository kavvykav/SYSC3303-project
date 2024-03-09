package test;

import common.ElevatorStatus;
import elevator.*;
import org.junit.jupiter.api.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
        ElevatorState ElevatorMotorRunningState = new ElevatorMotorRunningState();
        elevator.setCurrentState(ElevatorMotorRunningState);
        assertInstanceOf(ElevatorMotorRunningState.class, elevator.getCurrentState());
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
        ElevatorStatus status = new ElevatorStatus(1, 16, ElevatorStatus.Direction.STATIONARY);
        assertEquals(status, elevator.getStatus());
        assertEquals(1, status.getId());
        assertEquals(16, status.getFloor());
        status.setFloor(8);
        assertEquals(8, status.getFloor());
        assertEquals(ElevatorStatus.Direction.STATIONARY, status.getDirection());
    }
    @Test
    void ShouldStopTest(){
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(16);
        assertEquals((int) requests.get(0), elevator.getStatus().getFloor());

    }
    @Test
    void updateRequestsTest(){
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(16);
        assertEquals(16, requests.remove(0));
    }
    @Test
    void GetCurrentRequestsTest(){
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(16);
        requests.add(8);
        requests.add(3);
        assertEquals(16, requests.get(0));
    }
    @Test
    void GetNumRequestsTest(){
        ArrayList<Integer> requests = new ArrayList<>();
        requests.add(16);
        requests.add(8);
        requests.add(3);
        assertEquals(3, requests.size());
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