package test;

import org.junit.jupiter.api.*;
import scheduler.*;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    static Scheduler scheduler;
    @BeforeAll
    static void setUp() {
        scheduler = new Scheduler();
    }

    @Test
    void ResponseReceivedState() {
        SchedulerState SchedulerResponseReceivedState = new SchedulerResponseReceivedState();
        scheduler.setCurrentState(SchedulerResponseReceivedState);
        assertInstanceOf(SchedulerResponseReceivedState.class, scheduler.getCurrentState());
    }

//    @Test
//    void EstablishConnectionState(){
//        SchedulerState SchedulerEstablishConnectionState = new SchedulerEstablishConnectionState();
//        scheduler.setCurrentState(SchedulerEstablishConnectionState);
//        assertInstanceOf(SchedulerEstablishConnectionState.class, scheduler.getCurrentState());
//    }
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
//    @Test
//    void WaitState(){
//        SchedulerState SchedulerWaitState = new SchedulerWaitState();
//        scheduler.setCurrentState(SchedulerWaitState);
//        assertInstanceOf(scheduler.SchedulerWaitState.class, scheduler.getCurrentState());
//    }
}