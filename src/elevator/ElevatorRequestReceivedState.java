package elevator;

import common.Direction;
import common.FloorRequest;

/**
 * ElevatorMotorRunningState
 */
public class ElevatorRequestReceivedState implements ElevatorState {
    /**
     * In the motor running state, the elevator starts the motor if it is not currently running
     *
     * @param elevator The elevator object
     * @param request The request received by the elevator
     *
     * @return Always null in this case
     */
    public FloorRequest doAction(Elevator elevator, FloorRequest request) {

        // Save the direction of the elevator before handling the request
        Direction currentDirection = elevator.getStatus().getDirection();

        // Update the direction the elevator is bringing passengers
        elevator.getStatus().setGoingUp(request.isGoingUp());

        // Add the floor
        elevator.add(request.getFloor());

        // If not currently running, start the motor
        if (currentDirection == Direction.STATIONARY) {
            // Determine the direction the elevator needs to go
            if (request.getFloor() > elevator.getStatus().getFloor()) {
                elevator.getStatus().setDirection(Direction.UP);
            } else if (request.getFloor() < elevator.getStatus().getFloor()){
                elevator.getStatus().setDirection(Direction.DOWN);
            } else {
                Direction direction = request.isGoingUp() ? Direction.UP: Direction.DOWN;
                elevator.getStatus().setDirection(direction);
            }
            elevator.startMotor();
        }
        return null;
    }
}
