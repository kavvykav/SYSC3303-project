package scheduler;

import common.FloorRequest;

/**
 * This is the state for when the Scheduler receives a request from the Floor. It
 * will choose and send that request to an elevator.
 */
public class SchedulerRequestReceivedState implements SchedulerState {

    /**
     * The action the Scheduler performs when in the Request Received state
     */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest request) {

        ElevatorClient client = null;
        if (request != null) {
            scheduler.getStats().incrementNumRequests();
            client = scheduler.chooseElevator(request);
        }
        if (client != null) {
            if (scheduler.send(request, client.getAddress(), client.getPort()) != 0) {
                System.err.println("Scheduler: Failed to send Request to elevator");
            }
            scheduler.schedulerPrint("Assigned request to Elevator " + client.getStatus().getId());
        } else {
            scheduler.schedulerPrint("No elevators available");
            scheduler.getStats().incrementNumFailed();
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
