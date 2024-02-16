/**
 * This is the state for when the Scheduler receives a request. It will send
 * that
 * request off to the floor.
 */
public class SchedulerRequestReceivedState implements SchedulerState {

    private Scheduler scheduler;
    private FloorData data;

    /**
     * The constructor for the state object.
     *
     * @param scheduler : the context of the state
     */
    public SchedulerRequestReceivedState(Scheduler scheduler) {
        this.scheduler = scheduler;
        data = null;
    }

    /**
     * This method sets data to send.
     */
    public void chooseDataToSend(FloorData data) {
        this.data = data;
    }

    /**
     * The action the Scheduler performs when in the Request Received state
     */
    public void doAction() {
        ClientPacketData client = scheduler.getClient("elevator");
        if (data != null) {
            if (scheduler.send(data, client.getAddress(), client.getPort()) != 0) {
                System.err.println("Scheduler : failed to send FloorData to elevator");
            }
        }
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
