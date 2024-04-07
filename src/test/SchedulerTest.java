/*package test;

import common.Direction;
import common.ElevatorStatus;
import common.FloorRequest;
import org.junit.jupiter.api.*;
import scheduler.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    static Scheduler scheduler;
    static ElevatorClient elevator1;
    static ElevatorClient elevator2;
    static ElevatorClient elevator3;
    static ElevatorClient elevator4;
    @BeforeAll
    static void setUp() {
        scheduler = new Scheduler();
        InetAddress address;
        try {
        address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        throw new RuntimeException(e);
        }
        elevator1 = new ElevatorClient(address, 5007, 1);
        elevator2 = new ElevatorClient(address, 5007, 2);
        elevator3 = new ElevatorClient(address, 5007, 3);
        elevator4 = new ElevatorClient(address, 5007, 4);
    }

    @Test
    void ResponseReceivedState() {
        SchedulerState SchedulerResponseReceivedState = new SchedulerResponseReceivedState();
        scheduler.setCurrentState(SchedulerResponseReceivedState);
        assertInstanceOf(SchedulerResponseReceivedState.class, scheduler.getCurrentState());
    }
    @Test
    void IdleState(){
        SchedulerState SchedulerIdleState = new SchedulerIdleState();
        scheduler.setCurrentState(SchedulerIdleState);
        assertInstanceOf(scheduler.SchedulerIdleState.class, scheduler.getCurrentState());
    }
    @Test
    void RequestReceivedState(){
        SchedulerState SchedulerRequestReceivedState = new SchedulerRequestReceivedState();
        scheduler.setCurrentState(SchedulerRequestReceivedState);
        assertInstanceOf(scheduler.SchedulerRequestReceivedState.class, scheduler.getCurrentState());
    }
    @Test
    void GetClientTest(){
        ArrayList<ElevatorClient> elevators = new ArrayList<>();
        elevators.add(elevator1);
        elevators.add(elevator2);
        elevators.add(elevator3);
        elevators.add(elevator4);
        assertEquals(elevator1, elevators.get(0));
        assertEquals(elevator2, elevators.get(1));
        assertEquals(elevator3, elevators.get(2));
        assertEquals(elevator4, elevators.get(3));
        assertEquals(1,elevators.get(0).getStatus().getId());
        assertEquals(2,elevators.get(1).getStatus().getId());
        assertEquals(3,elevators.get(2).getStatus().getId());
        assertEquals(4,elevators.get(3).getStatus().getId());
    }
    @Test
    void ChooseElevatorTest(){
        FloorRequest req1 = new FloorRequest( 16, 0, false);
        elevator1.setStatus(new ElevatorStatus(1, 18, Direction.UP, true));
        elevator2.setStatus(new ElevatorStatus(2, 9, Direction.STATIONARY, false));
        elevator3.setStatus(new ElevatorStatus(3, 1, Direction.DOWN, true));
        elevator4.setStatus(new ElevatorStatus(4, 20, Direction.DOWN, false));
        scheduler.addClient(elevator1);
        scheduler.addClient(elevator2);
        scheduler.addClient(elevator3);
        scheduler.addClient(elevator4);
        assertEquals(elevator4, scheduler.chooseElevator(req1));

        FloorRequest req2 = new FloorRequest(20, 0, true);
        assertEquals(elevator1, scheduler.chooseElevator(req2));

        FloorRequest req3 = new FloorRequest(8, 0, true);
        assertEquals(elevator2, scheduler.chooseElevator(req3));

        FloorRequest req4 = new FloorRequest(20, 0, true);
        assertEquals(elevator1, scheduler.chooseElevator(req4));

        FloorRequest req5 = new FloorRequest(1, 0, true);
        assertEquals(elevator3, scheduler.chooseElevator(req5));

        FloorRequest req6 = new FloorRequest(22, 0, true);
        assertEquals(elevator1, scheduler.chooseElevator(req6));

        FloorRequest req7 = new FloorRequest(22, 0, false);
        assertEquals(elevator2, scheduler.chooseElevator(req7));

    }
    @Test
    void CanServiceRequestTest(){
        FloorRequest data = new FloorRequest(16, 0, true);
        elevator1.setStatus(new ElevatorStatus(1, 18, Direction.UP, true));
        elevator2.setStatus(new ElevatorStatus(2, 9, Direction.STATIONARY, false));
        elevator3.setStatus(new ElevatorStatus(3, 1, Direction.UP, true));
        elevator4.setStatus(new ElevatorStatus(4, 20, Direction.DOWN, false));
        assertFalse(scheduler.canServiceRequest(elevator1, data));
        assertTrue(scheduler.canServiceRequest(elevator2, data));
        assertTrue(scheduler.canServiceRequest(elevator3, data));
        assertFalse(scheduler.canServiceRequest(elevator4, data));

        FloorRequest data2 = new FloorRequest(9, 0, false);
        assertFalse(scheduler.canServiceRequest(elevator1, data2));
        assertTrue(scheduler.canServiceRequest(elevator2, data2));
        assertFalse(scheduler.canServiceRequest(elevator3, data2));
        assertTrue(scheduler.canServiceRequest(elevator4, data2));

        FloorRequest data3 = new FloorRequest(1, 0, false);
        assertFalse(scheduler.canServiceRequest(elevator1, data3));
        assertTrue(scheduler.canServiceRequest(elevator2, data3));
        assertFalse(scheduler.canServiceRequest(elevator3, data3));
        assertTrue(scheduler.canServiceRequest(elevator4, data3));

        FloorRequest data4 = new FloorRequest(22, 0, false);
        assertFalse(scheduler.canServiceRequest(elevator1, data4));
        assertTrue(scheduler.canServiceRequest(elevator2, data4));
        assertFalse(scheduler.canServiceRequest(elevator3, data4));
        assertFalse(scheduler.canServiceRequest(elevator4, data4));

        FloorRequest data5 = new FloorRequest(22, 0, true);
        assertTrue(scheduler.canServiceRequest(elevator1, data5));
        assertTrue(scheduler.canServiceRequest(elevator2, data5));
        assertTrue(scheduler.canServiceRequest(elevator3, data5));
        assertFalse(scheduler.canServiceRequest(elevator4, data5));

        FloorRequest data6 = new FloorRequest(1, 0, true);
        assertFalse(scheduler.canServiceRequest(elevator1, data6));
        assertTrue(scheduler.canServiceRequest(elevator2, data6));
        assertTrue(scheduler.canServiceRequest(elevator3, data6));
        assertFalse(scheduler.canServiceRequest(elevator4, data6));
    }
}*/