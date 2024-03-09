package elevator;

import common.ElevatorStatus;
import common.UDPClient;

import java.net.InetAddress;
import java.util.ArrayList;

public class Motor extends UDPClient implements Runnable {

    Elevator elevator;

    public Motor(Elevator elevator, InetAddress address, int port) {

        super(address, port);
        this.elevator = elevator;
    }

    public void run() {

        if (elevator.getStatus().getDirection() == ElevatorStatus.Direction.STATIONARY) {
            return;
        }
        ArrayList<Integer> requests = elevator.getRequests();

        while (true) {

            if (requests.get(0).equals(elevator.getStatus().getFloor())) {

                elevator.elevatorPrint("Stopping at floor " + elevator.getStatus().getFloor());
                Integer floor = requests.remove(0);
                elevator.openDoor();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }

                if (requests.isEmpty()) {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.STATIONARY);
                    send(elevator.getStatus());
                    break;
                }
                else if (requests.get(0) > floor) {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
                } else {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
                }
                send(elevator.getStatus());

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
            send(elevator.getStatus());
        }
    }
}
