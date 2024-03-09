package elevator;

import common.ElevatorStatus;
import java.util.ArrayList;

public class Motor implements Runnable {

    Elevator elevator;

    public Motor(Elevator elevator) {
        this.elevator = elevator;
    }

    public void run() {

        if (elevator.getStatus().getDirection() == ElevatorStatus.Direction.STATIONARY) {
            return;
        }

        ArrayList<Integer> requests = elevator.getRequests();
        while (true) {

//            elevator.elevatorPrint("Going to floor " + requests.get(0));
//            elevator.elevatorPrint("Currently at floor " + elevator.getStatus().getFloor());

            if (requests.get(0).equals(elevator.getStatus().getFloor())) {

                elevator.elevatorPrint("Stopping at floor " + elevator.getStatus().getFloor());
                requests.remove(0);
                elevator.openDoor();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }

                if (requests.isEmpty()) {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.STATIONARY);
                    elevator.sendStatus();
                    break;
                }
                elevator.elevatorPrint("Next Stop is floor " + requests.get(0));
                elevator.closeDoor();

            }
            try {
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                return;
            }
            int currentFloor = elevator.getStatus().getFloor();
            if (elevator.getStatus().getDirection() == ElevatorStatus.Direction.UP) {
                elevator.getStatus().setFloor(currentFloor + 1);
            } else {
                elevator.getStatus().setFloor(currentFloor - 1);
            }
            elevator.sendStatus();
        }
    }
}
