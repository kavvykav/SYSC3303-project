package elevator;

import common.FloorRequest;

/**
 * ElevatorState
 */
public interface ElevatorState {
    FloorRequest doAction(Elevator elevator, FloorRequest request);
}
