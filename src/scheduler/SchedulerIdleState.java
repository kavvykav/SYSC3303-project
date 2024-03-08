package scheduler;

import common.FloorData;

/**
 * This state is the scheduler.Scheduler waiting to receive the request.
 */
public class SchedulerIdleState implements SchedulerState {

    /**
     * The action that is performed when the scheduler.Scheduler is in the Idle state.
     */
    public FloorData doAction(Scheduler scheduler, FloorData data) {

        Object receivedObject = scheduler.receive();

        if (receivedObject instanceof FloorData) {
            // Received a request
            return (FloorData) receivedObject;
        } else if (receivedObject instanceof String) {
            // We have a new elevator connecting
            String type = (String) receivedObject;
            if (!type.equalsIgnoreCase("elevator")) {
                System.err.println("Scheduler: Invalid Client type: " + type);
            }
            ElevatorClient client = new ElevatorClient(scheduler.getReceivePacket());
            scheduler.addClient(client);
            return null;
        }
        return null;
    }

    /**
     * Returns the string representation of the state.
     *
     * @return the string representation of the state
     */
    public String toString() {
        return "Scheduler Idle State";
    }
}
