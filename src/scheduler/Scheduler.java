package scheduler;

import common.*;
import gui.GUI;

import java.lang.Math;

import java.util.ArrayList;

import static common.NetworkConstants.GUI_PORT;

/**
 * This is the main component of the scheduler.Scheduler Subsystem
 */
public class Scheduler extends UDPServer {

    // Number of elevators
    private static final int NUM_ELEVATORS = 4;

    // Port and IP Address
    private final ArrayList<ElevatorClient> elevators;

    // State of the Scheduler
    private SchedulerState currentState;

    //Elevator statistics
    private ElevatorStatistics stats;

    // Start and end times
    private long startTime;

    private long endTime;

    /**
     * The constructor for the scheduler.Scheduler object.
     */
    public Scheduler() {
        super();
        stats = new ElevatorStatistics();
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
     * Helper function for determining if the Elevator is available to server the
     * request.
     *
     * @param elevator The elevator to be checked
     * @param request  The request to be served
     *
     * @return True if the elevator can serve the request, false otherwise
     */
    public boolean canServiceRequest(ElevatorClient elevator, FloorRequest request) {

        ElevatorStatus status = elevator.getStatus();

        // If the elevator is stuck between floors, it cannot serve the request
        if (status.getDirection() == Direction.STUCK) {
            return false;
        }

        // If the elevator is not currently serving a request, it can serve the request
        if (status.getDirection() == Direction.STATIONARY) {
            return true;
        }

        // If the elevator is going up and the passenger wants to go up, check the floor
        // number of the request and the elevator
        if (status.getDirection() == Direction.UP && status.isGoingUp() && request.isGoingUp()) {
            return elevator.getStatus().getFloor() < request.getFloor();
        }

        // If the elevator is going down and the passenger wants to go down, check the
        // floor number of the request and the elevator
        if (status.getDirection() == Direction.DOWN && !status.isGoingUp() && !request.isGoingUp()) {
            return elevator.getStatus().getFloor() > request.getFloor();
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

        // Initialize the chosen elevator and the maximum distance, print error if user
        // started Floor before Elevator
        ElevatorClient chosenElevator = null;
        int maxDistance = 22;

        for (ElevatorClient elevator : elevators) {
            if (elevator.getStatus().getId() == request.getElevator()) {
                chosenElevator = elevator;
                break;
            }
            if (elevator.getStatus().getId() == request.getElevator() * -1) {
                continue;
            }
            // If the elevator can serve request and is closest, choose that elevator
            boolean canService = canServiceRequest(elevator, request);
            int distance = Math.abs(elevator.getStatus().getFloor() - request.getFloor());
            if (canService && distance <= maxDistance) {
                    maxDistance = distance;
                    chosenElevator = elevator;
            }
        }
        return chosenElevator;
    }

    /**
     * The main routine for the scheduler
     */
    public void serveRequests() { // Repeat indefinitely
        setStartTime(System.nanoTime());
        while (true) {
            // Idle state : wait for request from floor
            FloorRequest request = currentState.doAction(this, null);
            if (request == null) {
                continue;
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
     * 
     * @param output : what is being printed
     */
    public void schedulerPrint(String output) {
        System.out.println("Scheduler: " + output);
    }

    /**
     * Gets the statistics for the Elevator.
     *
     * @return stats: the data structure storing the statistics
     */
    public ElevatorStatistics getStats() {
        return stats;
    }

    /**
     * Sets the start time for the Scheduler.
     * @param startTime: the start time
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time for the Scheduler.
     * @param endTime: the end time
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the start time for the Scheduler.
     * @return startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time for the Scheduler.
     * @return endTime
     */
    public long getEndTime() {
        return endTime;
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.serveRequests();
    }
}
