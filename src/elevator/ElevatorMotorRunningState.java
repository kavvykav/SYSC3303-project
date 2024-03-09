package elevator;

import common.ElevatorStatus;
import common.FloorData;
import common.NetworkConstants;

import java.net.InetAddress;

import static common.NetworkConstants.SCHEDULER_PORT;

/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
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
        ElevatorStatus.Direction currentDirection = elevator.getStatus().getDirection();

        // Use the direction of the request to determine how to add the floors
        if (receivedData.returnDirection()) {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
        } else {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
        }
        elevator.add(receivedData.returnFloorNumber());
        elevator.add(receivedData.returnCarButton());

        // Determine the direction the elevator needs to go
        if (receivedData.returnFloorNumber() > elevator.getStatus().getFloor()) {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
        } else {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
        }

        // If not currently running, start the motor
        if (currentDirection == ElevatorStatus.Direction.STATIONARY) {
            InetAddress localHost = NetworkConstants.localHost();
            assert (localHost != null);
            Thread motor = new Thread(new Motor(elevator, localHost, SCHEDULER_PORT), "Motor");
            motor.start();
            elevator.elevatorPrint("Motor is running");
        }
        return null;
    }
}
