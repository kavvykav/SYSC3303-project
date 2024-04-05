package elevator;

import common.FloorData;

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
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        return (FloorData) elevator.receive();
    }
}
