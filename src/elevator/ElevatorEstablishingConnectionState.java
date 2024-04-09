package elevator;

import common.FloorRequest;

/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {

    /**
     * In the establishing connection state, the Elevator sends its initial status and information to the Scheduler.
     *
     * @param elevator The Elevator instance
     * @param request null in this case
     * @return null in this case
     */
    @Override
    public FloorRequest doAction(Elevator elevator, FloorRequest request) {
        elevator.elevatorPrint("Establishing connection with scheduler");
        elevator.setCurrentState(this);
        elevator.send(elevator.getStatus());
        return null;
    }
}
