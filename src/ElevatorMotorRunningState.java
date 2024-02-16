/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public void doAction(Elevator elevator) {
        System.out.println("Elevator Motor is running");
        elevator.setCurrentState(this);
    }
}
