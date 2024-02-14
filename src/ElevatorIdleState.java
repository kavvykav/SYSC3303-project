/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorTaskReceivedState());
    }

    public void printStatus() {
        System.out.println("Elevator is idle");
    }
}
