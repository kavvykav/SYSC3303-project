package scheduler;

import common.FloorData;

/**
 * This state is when the scheduler is waiting to receive a response back from
 * the floor.
 */
public class SchedulerWaitState implements SchedulerState {

    /**
     * The action that is performed when the scheduler.Scheduler is in the Wait state.
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
            System.err.println("Scheduler: Invalid object received.");
            return null;
        }
    }

    /**
     * Returns a string representation of the current state.
     *
     * @return a string representation of the state.
     */
    public String toString() {
        return "Scheduler Wait State";
    }
}
