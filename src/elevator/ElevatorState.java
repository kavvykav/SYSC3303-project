package elevator;

import common.FloorData;
import elevator.Elevator;

/**
 * ElevatorState
 */
public interface ElevatorState {
    FloorData doAction(Elevator elevator, FloorData receivedData);
}
