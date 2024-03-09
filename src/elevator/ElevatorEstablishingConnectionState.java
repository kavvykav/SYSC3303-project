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
        if (elevator.send("elevator") != 0) {
            elevator.elevatorPrint("Failed to send initial message");
            System.exit(1);
        }
        return null;
    }
}
