/**
 * This is the state where the Scheduler receives a response from the Elevator.
 * It will send it back to the floor.
 */
public class SchedulerResponseReceivedState implements SchedulerState {
    private Scheduler scheduler;
    private FloorData data;

    /**
     * The constructor for the state object.
     *
     * @param scheduler : the context for the state
     */
    public SchedulerResponseReceivedState(Scheduler scheduler) {
        this.scheduler = scheduler;
        data = null;
    }

    /**
     * This method sets the data to be sent.
     *
     * @param data: the FloorData object that is being sent
     */
    public void chooseDataToSend(FloorData data) {
        this.data = data;
    }

    /**
     * The action the Scheduler performs when in the Response Received state
     */
    public void doAction() {
        ClientPacketData client = scheduler.getClient("floor");
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
        return "Scheduler Response Received State";
    }
}
