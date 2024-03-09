package elevator;

import common.ElevatorStatus;
import common.FloorData;

/**
 * ElevatorTaskReceivedState
 */
public class ElevatorTaskReceivedState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {

        elevator.elevatorPrint("Received task from scheduler");


        return null;
    }
}
