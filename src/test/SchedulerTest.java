package test;

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
        elevator1 = new ElevatorClient(address, 5007, new ElevatorStatus(1, 18, ElevatorStatus.Direction.UP, 15));
        elevator2 = new ElevatorClient(address, 5007, new ElevatorStatus(2, 9, ElevatorStatus.Direction.STATIONARY, 15));
        elevator3 = new ElevatorClient(address, 5007, new ElevatorStatus(3, 1, ElevatorStatus.Direction.UP, 15));
        elevator4 = new ElevatorClient(address, 5007, new ElevatorStatus(4, 20, ElevatorStatus.Direction.DOWN, 15));
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
        FloorRequest data = new FloorRequest(1, "10:35:25", 16, true, 18, 2);
        scheduler.addClient(elevator1);
        scheduler.addClient(elevator2);
        scheduler.addClient(elevator3);
        scheduler.addClient(elevator4);
        assertEquals(elevator2, scheduler.chooseElevator(data));

        FloorRequest data2 = new FloorRequest(2, "12:35:25", 20, false, 8, 1);
        assertEquals(elevator4, scheduler.chooseElevator(data2));

        FloorRequest data3 = new FloorRequest(3, "2:35:25", 1, true, 8, 3);
        assertEquals(elevator3, scheduler.chooseElevator(data3));

        FloorRequest data4 = new FloorRequest(4, "3:35:25", 20, true, 22, 1);
        assertEquals(elevator1, scheduler.chooseElevator(data4));

        FloorRequest data5 = new FloorRequest(5, "3:35:25", 1, false, 1, 4);
        assertEquals(elevator2, scheduler.chooseElevator(data5));

        FloorRequest data6 = new FloorRequest(6, "3:35:25", 22, true, 22, 3);
        assertEquals(elevator1, scheduler.chooseElevator(data6));

        FloorRequest data7 = new FloorRequest(7, "3:35:25", 22, false, 1, 1);
        assertEquals(elevator2, scheduler.chooseElevator(data7));

    }
    @Test
    void CanServiceRequestTest(){
        FloorRequest data = new FloorRequest(1, "10:35:25", 16, true, 18, 3);
        assertFalse(scheduler.canServiceRequest(elevator1, data));
        assertTrue(scheduler.canServiceRequest(elevator2, data));
        assertTrue(scheduler.canServiceRequest(elevator3, data));
        assertFalse(scheduler.canServiceRequest(elevator4, data));

        FloorRequest data2 = new FloorRequest(2, "10:35:25", 9, false, 3, 2);
        assertFalse(scheduler.canServiceRequest(elevator1, data2));
        assertTrue(scheduler.canServiceRequest(elevator2, data2));
        assertFalse(scheduler.canServiceRequest(elevator3, data2));
        assertTrue(scheduler.canServiceRequest(elevator4, data2));

        FloorRequest data3 = new FloorRequest(3, "10:35:25", 1, false, 1, 1);
        assertFalse(scheduler.canServiceRequest(elevator1, data3));
        assertTrue(scheduler.canServiceRequest(elevator2, data3));
        assertFalse(scheduler.canServiceRequest(elevator3, data3));
        assertTrue(scheduler.canServiceRequest(elevator4, data3));

        FloorRequest data4 = new FloorRequest(4, "10:35:25", 22, false, 9, 4);
        assertFalse(scheduler.canServiceRequest(elevator1, data4));
        assertTrue(scheduler.canServiceRequest(elevator2, data4));
        assertFalse(scheduler.canServiceRequest(elevator3, data4));
        assertFalse(scheduler.canServiceRequest(elevator4, data4));

        FloorRequest data5 = new FloorRequest(5, "10:35:25", 22, true, 22, 1);
        assertTrue(scheduler.canServiceRequest(elevator1, data5));
        assertTrue(scheduler.canServiceRequest(elevator2, data5));
        assertTrue(scheduler.canServiceRequest(elevator3, data5));
        assertFalse(scheduler.canServiceRequest(elevator4, data5));

        FloorRequest data6 = new FloorRequest(6, "10:35:25", 1, true, 22, 2);
        assertFalse(scheduler.canServiceRequest(elevator1, data6));
        assertTrue(scheduler.canServiceRequest(elevator2, data6));
        assertTrue(scheduler.canServiceRequest(elevator3, data6));
        assertFalse(scheduler.canServiceRequest(elevator4, data6));
    }
}