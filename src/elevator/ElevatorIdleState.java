package elevator;

import common.FloorRequest;

/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    /**
     * When in the idle state, the Elevator simply waits for a request
     *
     * @param elevator The Elevator object
     * @param receivedData Always null in this case
     *
     * @return The request received by the elevator
     */
    public FloorRequest doAction(Elevator elevator, FloorRequest receivedData) {
        return (FloorRequest) elevator.receive();
    }
}
