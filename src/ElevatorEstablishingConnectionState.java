/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {
    @Override
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator: Establishing connection with Scheduler");
        elevator.setCurrentState(this);
        if (elevator.send("elevator") != 0) {
            System.err.println("Elevator: Failed to send initial message");
            System.exit(1);
        }
        return null;
    }
}
