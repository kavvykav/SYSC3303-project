package elevator;

import common.FloorData;

/**
 * ElevatorTaskReceivedState
 */
public class ElevatorTaskReceivedState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator: Received task from scheduler");
        return null;
    }
}
