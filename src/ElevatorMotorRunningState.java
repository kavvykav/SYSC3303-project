/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public void next(Elevator elevator) {
        elevator.setState(new ElevatorDestinationReachedState());
    }

    public void printStatus() {
        System.out.println("Elevator's motor is running");
    }
}
