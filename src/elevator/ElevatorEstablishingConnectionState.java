package elevator;

import common.FloorRequest;

/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {
    @Override
    public FloorRequest doAction(Elevator elevator, FloorRequest request) {
        elevator.elevatorPrint("Establishing connection with scheduler");
        elevator.setCurrentState(this);
        elevator.send(elevator.getStatus());
        return null;
    }
}
