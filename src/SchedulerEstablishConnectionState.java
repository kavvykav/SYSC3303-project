/**
 * This is the state that establishes the initial connection with the Floor and
 * Elevator subsystems.
 */
public class SchedulerEstablishConnectionState implements SchedulerState {

    /**
     * This is the action method that establishes the initial connections.
     */
    public FloorData doAction(Scheduler scheduler, FloorData data) {
        // Iterate 2x to establish a connection with the other 2 subsystems
        for (int i = 0; i < 2; i++) {
            Object receivedObject = scheduler.receive();
            if (receivedObject instanceof String) {
                String type = (String) receivedObject;
                if (!type.equalsIgnoreCase("floor") && !type.equalsIgnoreCase("elevator")) {
                    System.err.println("Scheduler: Invalid Client type");
                }
                ClientPacketData client = new ClientPacketData(scheduler.getReceivePacket(), type.toLowerCase());

                System.out.println("Scheduler: Successfully established a connection with the " + receivedObject);
                scheduler.addClient(client);
            } else {
                System.err.println("Invalid type of object received");
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the state.
     *
     * @return : the string representation of the currentr state
     */
    public String toString() {
        return "Scheduler Establish Connection State";
    }
}
