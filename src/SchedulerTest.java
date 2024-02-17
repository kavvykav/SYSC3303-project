
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    Scheduler scheduler;
    @BeforeEach
    void setUp() {
        scheduler = new Scheduler();
    }
    @Test
    void ResponseReceivedState(){
        SchedulerState SchedulerResponseReceivedState = new SchedulerResponseReceivedState();
        scheduler.setCurrentState(SchedulerResponseReceivedState);
        assertInstanceOf(SchedulerResponseReceivedState.class, scheduler.getCurrentState());
    }
    @Test
    void EstablishConnectionState(){
        SchedulerState SchedulerEstablishConnectionState = new SchedulerEstablishConnectionState();
        scheduler.setCurrentState(SchedulerEstablishConnectionState);
        assertInstanceOf(SchedulerEstablishConnectionState.class, scheduler.getCurrentState());
    }
    @Test
    void IdleState(){
        SchedulerState SchedulerIdleState = new SchedulerIdleState();
        scheduler.setCurrentState(SchedulerIdleState);
        assertInstanceOf(SchedulerIdleState.class, scheduler.getCurrentState());
    }
    @Test
    void RequestReceivedState(){
        SchedulerState SchedulerRequestReceivedState = new SchedulerRequestReceivedState();
        scheduler.setCurrentState(SchedulerRequestReceivedState);
        assertInstanceOf(SchedulerRequestReceivedState.class, scheduler.getCurrentState());
    }
    @Test
    void WaitState(){
        SchedulerState SchedulerWaitState = new SchedulerWaitState();
        scheduler.setCurrentState(SchedulerWaitState);
        assertInstanceOf(SchedulerWaitState.class, scheduler.getCurrentState());
    }
}