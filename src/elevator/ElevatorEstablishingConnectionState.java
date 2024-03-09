package elevator;

import common.FloorData;

/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {
    @Override
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        elevator.elevatorPrint("Establishing connection with scheduler");
        elevator.setCurrentState(this);
        elevator.sendStatus();
        return null;
    }
}
