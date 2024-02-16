/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    public void doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator is idle");
        elevator.setCurrentState(this);
    }
}
