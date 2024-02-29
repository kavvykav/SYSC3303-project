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

            FloorData receivedData = (FloorData) receivedObject;
            String type = receivedData.getStatus() ? "floor" : "elevator";
            SchedulerClient client = scheduler.getClient(type.toLowerCase());
            if (client == null) {
                System.err.println("Scheduler: Message from unknown " + type);
            }
            System.out.println("Scheduler: Got FloorData from " + type);
            return receivedData;
        } else {
            System.err.println("Scheduler: Invalid type of object received.");
            return null;
        }
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
