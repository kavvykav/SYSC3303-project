/**
 * ElevatorDestinationReached
 */
public class ElevatorDestinationReachedState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorIdleState());
    }

    public void PrintStatus() {
        System.out.println("Elevator has reached its destination");
    }
}
