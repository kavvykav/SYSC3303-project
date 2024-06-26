package elevator;

import common.Direction;
import common.UDPClient;

import java.net.InetAddress;
import java.util.Random;

/**
 * Motor simulates the movement of the Elevator. It is implemented as thread so
 * the Elevator can receive requests
 * from the Scheduler while it is running.
 */
public class Motor extends UDPClient implements Runnable {

    // The elevator instance
    private final Elevator elevator;

    // For injecting faults
    private final Random rand;

    // Hard fault rate (1 in HARD_FAULT_RATE)
    private static final int HARD_FAULT_RATE = 100;

    // Soft fault rate (1 in SOFT_FAULT_RATE)
    private static final int SOFT_FAULT_RATE = 5;

    // Time to hold the elevator door open for
    private static final int OPEN_TIME = 5;

    /**
     * Creates a Motor instance
     *
     * @param elevator The Elevator instance
     * @param address  The address of the Scheduler
     * @param port     The port the Scheduler is listening on
     */
    public Motor(Elevator elevator, InetAddress address, int port) {
        super(address, port);
        this.elevator = elevator;
        rand = new Random();
    }

    /**
     * Returns the elevator that the motor uses. For testing purposes only.
     *
     * @return the elevator object.
     */
    public Elevator getElevator() {
        return elevator;
    }

    /**
     * The main sequence for the Motor thread. Goes to the floors in the Elevator's list of requests, opening and
     * closing the door at each stop. Exits when there are no more requests to serve.
     */
    public void run() {

        // If the elevator is not moving or there are no requests, return
        if (elevator.getStatus().getDirection() == Direction.STATIONARY ||
                elevator.getNumRequests() == 0) {
            return;
        }
        while (true) {

            // Check if the floor is a requested stop
            if (elevator.shouldStop()) {

                // Remove the request as it has been served
                elevator.elevatorPrint("Stopping at floor " + elevator.getStatus().getFloor());
                Integer floor = elevator.updateRequests();

                // Let the elevator know that we have stopped at floor
                elevator.getStatus().setStopped(true);
                send(elevator.getStatus());

                // Simulate opening and closing the door
                elevator.openDoor();
                try {
                    Thread.sleep(OPEN_TIME * 1000L);
                } catch (InterruptedException e) {
                    return;
                }
                elevator.getStatus().setStopped(false);
                try {
                    elevator.closeDoor(SOFT_FAULT_RATE, rand);
                    elevator.elevatorPrint("Elevator door successfully closed");
                } catch (Exception e) {
                    Direction previousDirection = elevator.getStatus().getDirection();
                    elevator.getStatus().setDirection(Direction.DOOR_STUCK);
                    elevator.elevatorPrint("Elevator door is stuck open, trying again");
                    send(elevator.getStatus());

                    // Sleep for a random amount of time between 5 and 15 seconds to simulate the door being stuck
                    int stuckTime = rand.nextInt(10000) + 5000;
                    try {
                        Thread.sleep(stuckTime);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                        System.exit(1);
                    }
                    elevator.forceCloseDoor();
                    elevator.elevatorPrint("Elevator door successfully closed " +
                            "after being stuck for " + stuckTime / 1000 + " seconds");
                    elevator.getStatus().setDirection(previousDirection);
                    send(elevator.getStatus());
                }

                // Check if we have any more requests and update the status accordingly
                if (elevator.getNumRequests() == 0) {
                    elevator.getStatus().setDirection(Direction.STATIONARY);
                    send(elevator.getStatus());
                    return;
                } else if (elevator.getCurrentRequest() > floor) {
                    elevator.getStatus().setDirection(Direction.UP);
                } else if (elevator.getCurrentRequest() < floor) {
                    elevator.getStatus().setDirection(Direction.DOWN);
                }
                send(elevator.getStatus());

                elevator.elevatorPrint("Next Stop is floor " + elevator.getCurrentRequest());
            }
            // Simulate going from one floor to another
            int sleepTime = (rand.nextInt(HARD_FAULT_RATE) == 1) ? 10 : 5;
            elevator.startTimer(8);
            try {
                Thread.sleep(sleepTime * 1000L);
            } catch (InterruptedException e) {
                elevator.getStatus().setDirection(Direction.STUCK);
                elevator.elevatorPrint("Stuck between floors, shutting down");
                send(elevator.getStatus());
                return;
            }
            elevator.stopTimer(); // We have met the deadline, stop the timer

            // Update fields
            int currentFloor = elevator.getStatus().getFloor();
            int nextFloor;
            if (elevator.getStatus().getDirection() == Direction.UP) {
                nextFloor = Math.min(currentFloor + 1, elevator.getNumFloors());
            } else {
                nextFloor = Math.max(currentFloor - 1, 1);
            }
            elevator.getStatus().setFloor(nextFloor);
            send(elevator.getStatus());
        }
    }
}
