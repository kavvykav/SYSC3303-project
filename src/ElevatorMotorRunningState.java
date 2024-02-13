/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorDestinationReachedState());
    }

    public void PrintStatus() {
        System.out.println("Elevator's motor is running");
    }
}
