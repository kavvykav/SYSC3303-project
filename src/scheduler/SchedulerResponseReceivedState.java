package scheduler;

import common.FloorRequest;

/**
 * This is the state where the scheduler.Scheduler receives a response from the elevator.Elevator.
 * It will send it back to the floor.
 * This class is currently deprecated
 */
public class SchedulerResponseReceivedState implements SchedulerState {

    /**
     * The action the scheduler.Scheduler performs when in the Response Received state
     */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest request) {
        return null;
    }

    /**
     * Returns a string representation of the state.
     *
     * @return a string representation of the state.
     */
    public String toString() {
        return "Scheduler Response Received State";
    }
}
