
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
        SchedulerState SchedulerResponseReceivedState = new SchedulerResponseReceivedState(scheduler);
        scheduler.setCurrentState(SchedulerResponseReceivedState);
        assertEquals(scheduler.getCurrentState(), SchedulerResponseReceivedState);
    }
    @Test
    void EstablishConnectionState(){
        SchedulerState SchedulerEstablishConnectionState = new SchedulerEstablishConnectionState(scheduler);
        scheduler.setCurrentState(SchedulerEstablishConnectionState);
        assertEquals(scheduler.getCurrentState(), SchedulerEstablishConnectionState);
    }
    @Test
    void IdleState(){
        SchedulerState SchedulerIdleState = new SchedulerIdleState(scheduler);
        scheduler.setCurrentState(SchedulerIdleState);
        assertEquals(scheduler.getCurrentState(), SchedulerIdleState);
    }
    @Test
    void RequestReceivedState(){
        SchedulerState SchedulerRequestReceivedState = new SchedulerRequestReceivedState(scheduler);
        scheduler.setCurrentState(SchedulerRequestReceivedState);
        assertEquals(scheduler.getCurrentState(), SchedulerRequestReceivedState);
    }
    @Test
    void WaitState(){
        SchedulerState SchedulerWaitState = new SchedulerWaitState(scheduler);
        scheduler.setCurrentState(SchedulerWaitState);
        assertEquals(scheduler.getCurrentState(), SchedulerWaitState);
    }
}