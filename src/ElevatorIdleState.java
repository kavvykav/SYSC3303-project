/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorTaskReceivedState());
    }

    public void PrintStatus() {
        System.out.println("Elevator is idle");
    }
}
