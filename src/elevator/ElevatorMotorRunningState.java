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
    public FloorData doAction(Elevator elevator, FloorData receivedData) {

        System.out.println("Motor state");

        ElevatorStatus.Direction currentDirection = elevator.getStatus().getDirection();

        if (receivedData.returnFloorNumber() < receivedData.returnCarButton()) {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
        } else {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
        }

        elevator.add(receivedData.returnFloorNumber());
        elevator.add(receivedData.returnCarButton());

        if (receivedData.returnFloorNumber() > elevator.getStatus().getFloor()) {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
        } else {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
        }
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
