/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator Motor is running");
        return null;
    }
}
