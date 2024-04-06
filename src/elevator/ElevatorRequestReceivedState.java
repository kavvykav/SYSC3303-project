package elevator;

import common.Direction;
import common.FloorData;

/**
 * ElevatorMotorRunningState
 */
public class ElevatorRequestReceivedState implements ElevatorState {
    /**
     * In the motor running state, the elevator starts the motor if it is not currently running
     *
     * @param elevator The elevator object
     * @param receivedData The request received by the elevator
     *
     * @return Always null in this case
     */
    public FloorData doAction(Elevator elevator, FloorData receivedData) {

        // Look at the current direction of the elevator
        Direction currentDirection = elevator.getStatus().getDirection();

        // Use the direction of the request to determine how to add the floors
        if (receivedData.returnDirection()) {
            elevator.getStatus().setDirection(Direction.UP);
        } else {
            elevator.getStatus().setDirection(Direction.DOWN);
        }
        elevator.add(receivedData.returnFloorNumber());
        elevator.add(receivedData.returnCarButton());

        // Determine the direction the elevator needs to go
        if (receivedData.returnFloorNumber() > elevator.getStatus().getFloor()) {
            elevator.getStatus().setDirection(Direction.UP);
        } else {
            elevator.getStatus().setDirection(Direction.DOWN);
        }

        // If not currently running, start the motor
        if (currentDirection == Direction.STATIONARY) {
            elevator.startMotor();
        }
        return null;
    }
}
