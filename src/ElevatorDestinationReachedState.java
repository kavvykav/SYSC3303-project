/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public void doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator has reached destination");
    }
}
