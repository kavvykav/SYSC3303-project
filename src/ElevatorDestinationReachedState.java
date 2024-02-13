/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorIdleState());
    }

    public void printStatus() {
        System.out.println("Elevator has reached its destination");
    }
}
