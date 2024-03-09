package elevator;

import common.FloorData;

/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        receivedData = (FloorData) elevator.receive();
        return receivedData;
    }
}
