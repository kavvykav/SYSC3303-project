package elevator;

import common.ElevatorStatus;
import common.UDPClient;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Motor simulates the movement of the Elevator. It is implemented as thread so the Elevator can receive requests
 * from the Scheduler while it is running.
 */
public class Motor extends UDPClient implements Runnable {

    // The elevator instance
    Elevator elevator;

    /**
     * Creates a Motor instance
     *
     * @param elevator The Elevator instance
     * @param address The address of the Scheduler
     * @param port The port the Scheduler is listening on
     */
    public Motor(Elevator elevator, InetAddress address, int port) {

        super(address, port);
        this.elevator = elevator;
    }

    /**
     * The main sequence for the Motor thread. Goes to the floors in the Elevator's list of requests, opening and
     * closing the door at each stop. Exits when there are no more requests to serve.
     */
    public void run() {

        // If the elevator is not moving or there are no requests, return
        if (elevator.getStatus().getDirection() == ElevatorStatus.Direction.STATIONARY ||
                elevator.getNumRequests() == 0) {
            return;
        }
        while (true) {

            // Check if the floor is a requested stop
            if (elevator.shouldStop()) {

                // Remove the request as it has been served
                elevator.elevatorPrint("Stopping at floor " + elevator.getStatus().getFloor());
                Integer floor = elevator.updateRequests();

                // Simulate opening and closing the door
                elevator.openDoor();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }

                // Check if we have any more requests and update the status accordingly
                if (elevator.getNumRequests() == 0) {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.STATIONARY);
                    send(elevator.getStatus());
                    break;
                }
                else if (elevator.getCurrentRequest() > floor) {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.UP);
                } else {
                    elevator.getStatus().setDirection(ElevatorStatus.Direction.DOWN);
                }
                send(elevator.getStatus());

                elevator.elevatorPrint("Next Stop is floor " + elevator.getCurrentRequest());
                elevator.closeDoor();
            }

            // Simulate going from one floor to another
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
