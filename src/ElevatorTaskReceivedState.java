/**
 * ElevatorTaskReceivedState
 */
public class ElevatorTaskReceivedState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorMotorRunningState());
    }

    public void printStatus() {
        System.out.println("Elevator has received a task from the scheduler");
    }
}
