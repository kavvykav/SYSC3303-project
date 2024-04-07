package scheduler;

import common.Direction;
import common.FloorRequest;
import common.UDPServer;

import java.lang.Math;
import java.util.ArrayList;

/**
 * This is the main component of the scheduler.Scheduler Subsystem
 */
public class Scheduler extends UDPServer implements Runnable {

    // Number of elevators
    private static final int NUM_ELEVATORS = 4;

    // Port and IP Address
    private final ArrayList<ElevatorClient> elevators;

    // State of the Scheduler
    private SchedulerState currentState;

    /**
     * The constructor for the scheduler.Scheduler object.
     */
    public Scheduler(int port) {
        super(port);
        elevators = new ArrayList<>(NUM_ELEVATORS);
        setCurrentState(new SchedulerIdleState());
    }

    /**
     * Adds a client to the ArrayList if it's not already there
     *
     * @param client : the client we want to add
     */
    public void addClient(ElevatorClient client) {
        if (!elevators.contains(client)) {
            elevators.add(client);
        }
    }

    public ElevatorClient getClient(int id) {

        for (ElevatorClient client : elevators) {
            if (client.getStatus().getId() == id) {
                return client;
            }
        }
        return null;
    }

    /**
     * Returns the current state.
     *
     * @return the current state of the scheduler.Scheduler
     */
    public SchedulerState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the scheduler.
     *
     * @param state : the state the scheduler.Scheduler is being set to.
     */
    public void setCurrentState(SchedulerState state) {
        this.currentState = state;
    }

    /**
     * Helper function for determining if the Elevator is available to server the request.
     *
     * @param elevator The elevator to be checked
     * @param request The request to be served
     *
     * @return True if the elevator can serve the request, false otherwise
     */
    public boolean canServiceRequest(ElevatorClient elevator, FloorRequest request) {
        // If the elevator is stuck between floors, it cannot serve the request
        if (elevator.getStatus().getDirection() == Direction.STUCK) {
            return false;
        }

        // If the elevator is not currently serving a request, it can serve the request
        if (elevator.getStatus().getDirection() == Direction.STATIONARY) {
            return true;
        }

        // If the elevator is going up and the passenger wants to go up, check the floor number
        if (elevator.getStatus().isGoingUp() && request.isGoingUp()) {
            return elevator.getStatus().getFloor() <= request.getFloor();
        }

        // If the elevator is going down and the passenger wants to go down, check the floor number
        if (!elevator.getStatus().isGoingUp() && !request.isGoingUp()) {
            return elevator.getStatus().getFloor() >= request.getFloor();
        }
        return false;
    }

    /**
     * Algorithm for determining which elevator to use for a request.
     *
     * @param request The request information
     *
     * @return The elevator to use
     */
    public ElevatorClient chooseElevator(FloorRequest request) {

        // Initialize the chosen elevator and the maximum distance, print error if user started Floor before Elevator
        ElevatorClient chosenElevator = null;
        int maxDistance = 22;

        for (ElevatorClient elevator : elevators) {
            if (elevator.getStatus().getId() == request.getElevator() * -1) {
                continue;
            }
            boolean canService = canServiceRequest(elevator, request);
            int distance = Math.abs(elevator.getStatus().getFloor() - request.getFloor());

            // If the elevator can serve request and is closest, choose that elevator
            if (canService) {
                if (elevator.getStatus().getId() == request.getElevator()) {
                    chosenElevator = elevator;
                    break;
                }
                else if (distance <= maxDistance) {
                    maxDistance = distance;
                    chosenElevator = elevator;
                }
            }
        }
        return chosenElevator;
    }

    /**
     * The main routine for the scheduler
     */
     public void run() {        // Repeat indefinitely
        while (true) {

            // Idle state : wait for request from floor
            FloorRequest request = currentState.doAction(this, null);
            if (request == null) {
                continue; // Remain in the idle state
            }

            // We have received a request, check the status of it
            schedulerPrint("Received a new Request From the Floor");
            setCurrentState(new SchedulerRequestReceivedState());

            currentState.doAction(this, request);
            setCurrentState(new SchedulerIdleState());
            schedulerPrint("Waiting for a request");
        }
    }

    /**
     * Wrapper method to print Scheduler information.
     * @param output : what is being printed
     */
    public void schedulerPrint(String output) {
        System.out.println("Scheduler: " + output);
    }


}
