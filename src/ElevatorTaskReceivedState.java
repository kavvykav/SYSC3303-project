/**
 * ElevatorTaskReceivedState
 */
public class ElevatorTaskReceivedState implements ElevatorState {
    public void doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator has received a task from scheduler");
        elevator.setCurrentState(this);
    }
}
