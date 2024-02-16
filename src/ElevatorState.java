/**
 * ElevatorState
 */
public interface ElevatorState {
    FloorData doAction(Elevator elevator, FloorData receivedData);
}
