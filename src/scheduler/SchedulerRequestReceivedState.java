package scheduler;

import common.FloorRequest;

/**
 * This is the state for when the scheduler.Scheduler receives a request. It
 * will send
 * that
 * request off to the floor.
 */
public class SchedulerRequestReceivedState implements SchedulerState {

    /**
     * The action the scheduler.Scheduler performs when in the Request Received
     * state
     */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest request) {

        ElevatorClient client = null;
        if (request != null) {
            client = scheduler.chooseElevator(request);
        }
        if (client != null) {
            if (scheduler.send(request, client.getAddress(), client.getPort()) != 0) {
                System.err.println("Scheduler: Failed to send FloorData to elevator");
            }
            scheduler.getStats().incrementNumRequests();
            scheduler.schedulerPrint("Assigned request to Elevator " + client.getStatus().getId());
        } else {
            scheduler.schedulerPrint("No elevators available");
        }
        return null;
    }

    /**
     * Returns a string representation of the state.
     *
     * @return a string representation of the state.
     */
    public String toString() {
        return "Scheduler Request Received State";
    }
}
