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
        SchedulerClient client = scheduler.getClient("floor");
        if (data != null) {
            if (scheduler.send(data, client.getAddress(), client.getPort()) != 0) {
                System.err.println("Scheduler: failed to send common.FloorData to elevator");
            }
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
