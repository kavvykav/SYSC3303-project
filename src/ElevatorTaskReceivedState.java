/**
 * ElevatorTaskReceivedState
 */
public class ElevatorTaskReceivedState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator has received a task from scheduler");
        return null;
    }
}
