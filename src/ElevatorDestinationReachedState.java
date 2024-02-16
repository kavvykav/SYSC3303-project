/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public void doAction(Elevator elevator) {
        System.out.println("Elevator has reached destination");
        elevator.setCurrentState(this);
    }
}
