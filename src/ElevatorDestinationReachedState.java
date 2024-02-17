/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        receivedData.setStatus(true);
        if (elevator.send(receivedData) != 0) {
            System.err.println("Elevator: Failed to respond to Scheduler");
        }
        System.out.println("Elevator: Reached destination");
        return null;
    }
}
