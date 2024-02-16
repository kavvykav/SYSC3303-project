/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator has reached destination");
        return null;
    }
}
