import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Elevator elevator;
    Scheduler scheduler;
    Floor floor;
    void testState(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        scheduler = new Scheduler();
        elevator = new Elevator(20, address, 5007);
        floor = new Floor("src/test_input.txt", address, 5007);

        assertEquals(ElevatorEstablishingConnectionState, elevator.getCurrentState());
        assertEquals(ElevatorIdleState, elevator.getCurrentState());

        assertEquals(SchedulerEstablishConnectionState, scheduler.getCurrentState());
        assertEquals(SchedulerIdleState, scheduler.getCurrentState());

        if (scheduler.getFloorData() != null){
            assertEquals(SchedulerRequestReceivedState, scheduler.getCurrentState());
        }
        if (scheduler.sendData() != null){
            assertEquals(SchedulerWaitState, scheduler.getCurrentState());
        }
        if (elevator.getFloorData() != null){
            assertEquals(ElevatorTaskReceivedState, elevator.getCurrentState());
        }
        if(elevator.motorRunning() != false){
            assertEquals(ElevatorMotorRunningState, elevator.getCurrentState());
        }
        if(elevator.motorRunning() == false){
            assertEquals(ElevatorDestinationReachedState, elevator.getCurrentState());
            assertEquals(ElevatorIdleState, elevator.getCurrentState());
        }

    }
}