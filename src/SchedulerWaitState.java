/**
 * This state is when the scheduler is waiting to receive a response back from
 * the floor.
 */
public class SchedulerWaitState implements SchedulerState {

    private Scheduler scheduler;
    private Object receivedObject;

    /**
     * The constructor for the state object.
     *
     * @param scheduler : the context for the state.
     */
    public SchedulerWaitState(Scheduler scheduler) {
        this.scheduler = scheduler;
        receivedObject = null;
    }

    /**
     * The action that is performed when the Scheduler is in the Wait state.
     */
    public void doAction() {

        receivedObject = scheduler.receive();
        if (receivedObject instanceof FloorData) {

            FloorData receivedData = (FloorData) receivedObject;
            String type = receivedData.getStatus() ? "Floor" : "Elevator";
            ClientPacketData client = scheduler.getClient(type.toLowerCase());
            if (client == null) {
                System.err.println("Scheduler: Message from unknown " + type);
            }
            System.out.println("Scheduler: Got FloorData from " + type);
        } else {
            System.err.println("Invalid type of object received.");
        }
    }

    /**
     * Returns the data received while in this state.
     *
     * @return a FloorData object containing the data
     */
    public FloorData getReceivedData() {
        return (FloorData) receivedObject;
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
