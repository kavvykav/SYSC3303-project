package elevator;

import common.ElevatorStatus;
import common.FloorData;

/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {

        System.out.println("Motor state");

        ElevatorStatus.Direction currentDirection = elevator.getStatus().getDirection();
        if (receivedData.returnFloorNumber() > elevator.getStatus().getFloor()) {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
        } else {
            elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
        }

        elevator.add(receivedData.returnFloorNumber());
        elevator.add(receivedData.returnCarButton());

        if (currentDirection == ElevatorStatus.Direction.STATIONARY) {

            Thread motor = new Thread(new Motor(elevator), "Motor");
            motor.start();
            elevator.elevatorPrint("Motor is running");
        }
        return null;
    }
}
