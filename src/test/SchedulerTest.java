package test;

import common.ElevatorStatus;
import common.FloorData;
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
        FloorData data = new FloorData("10:35:25", 16, true, 18);
        elevator1.setStatus(new ElevatorStatus(1, 18, ElevatorStatus.Direction.UP));
        elevator2.setStatus(new ElevatorStatus(2, 9, ElevatorStatus.Direction.STATIONARY));
        elevator3.setStatus(new ElevatorStatus(3, 1, ElevatorStatus.Direction.UP));
        elevator4.setStatus(new ElevatorStatus(4, 20, ElevatorStatus.Direction.DOWN));
        scheduler.addClient(elevator1);
        scheduler.addClient(elevator2);
        scheduler.addClient(elevator3);
        scheduler.addClient(elevator4);
        assertEquals(elevator2, scheduler.chooseElevator(data));

        FloorData data2 = new FloorData("12:35:25", 20, false, 8);
        assertEquals(elevator4, scheduler.chooseElevator(data2));
    }
    @Test
    void CanServiceRequestTest(){
        FloorData data = new FloorData("10:35:25", 16, true, 18 );
        elevator1.setStatus(new ElevatorStatus(1, 18, ElevatorStatus.Direction.UP));
        elevator2.setStatus(new ElevatorStatus(2, 9, ElevatorStatus.Direction.STATIONARY));
        elevator3.setStatus(new ElevatorStatus(3, 1, ElevatorStatus.Direction.UP));
        elevator4.setStatus(new ElevatorStatus(4, 20, ElevatorStatus.Direction.DOWN));
        assertFalse(scheduler.canServiceRequest(elevator1, data));
        assertTrue(scheduler.canServiceRequest(elevator2, data));
        assertTrue(scheduler.canServiceRequest(elevator3, data));
        assertFalse(scheduler.canServiceRequest(elevator4, data));
    }
}