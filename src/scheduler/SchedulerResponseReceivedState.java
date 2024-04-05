package scheduler;

import common.FloorData;

/**
 * This is the state where the scheduler.Scheduler receives a response from the elevator.Elevator.
 * It will send it back to the floor.
 */
public class SchedulerResponseReceivedState implements SchedulerState {

    /**
     * The action the scheduler.Scheduler performs when in the Response Received state
     */
    public FloorData doAction(Scheduler scheduler, FloorData data) {

        if (data.getStatus()) {
            scheduler.schedulerPrint("Elevator request served successfully");
        } else {
            System.err.println("Scheduler: Elevator request was not served");
        }
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
