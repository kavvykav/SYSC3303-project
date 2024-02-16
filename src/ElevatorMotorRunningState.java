/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public void doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator Motor is running");
        elevator.setCurrentState(this);
    }
}
