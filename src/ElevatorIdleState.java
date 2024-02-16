/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator: Idle");
        receivedData = (FloorData) elevator.receive();
        return receivedData;
    }
}
