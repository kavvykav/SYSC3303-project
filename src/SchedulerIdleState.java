/**
 * This state is the Scheduler waiting to receive the request.
 */
public class SchedulerIdleState implements SchedulerState {

    private Scheduler scheduler;
    private Object receivedObject;

    /**
     * The constructor for the State object
     *
     * @param Scheduler : the context of the state
     */
    public SchedulerIdleState(Scheduler scheduler) {
        this.scheduler = scheduler;
        receivedObject = null;
    }

    /**
     * The action that is performed when the Scheduler is in the Idle state.
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
            System.err.println("Scheduler : Invalid type of object received.");
        }
    }

    /**
     * Returns the received data.
     *
     * @return the received data
     */
    public FloorData getReceivedData() {
        return (FloorData) receivedObject;
    }

    /**
     * Returns the string represention of the state.
     *
     * @return the string representation of the state
     */
    public String toString() {
        return "Scheduler Idle State";
    }
}
