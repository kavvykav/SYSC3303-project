/**
 * This is the state for when the Scheduler receives a request. It will send
 * that
 * request off to the floor.
 */
public class SchedulerRequestReceivedState implements SchedulerState {

    /**
     * The action the Scheduler performs when in the Request Received state
     */
    public FloorData doAction(Scheduler scheduler, FloorData data) {
        ClientPacketData client = scheduler.getClient("elevator");
        if (data != null) {
            if (scheduler.send(data, client.getAddress(), client.getPort()) != 0) {
                System.err.println("Scheduler: failed to send FloorData to elevator");
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
        return "Scheduler Request Received State";
    }
}
