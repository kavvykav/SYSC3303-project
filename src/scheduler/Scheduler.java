package scheduler;

import common.ElevatorStatus;
import common.FloorData;
import common.UDPServer;

import java.lang.Math;
import java.net.DatagramPacket;
import java.util.ArrayList;

/**
 * This is the main component of the scheduler.Scheduler Subsystem
 */
public class Scheduler extends UDPServer {

    // Number of elevators
    private static final int NUM_ELEVATORS = 4;

    // Port and IP Address
    private ArrayList<ElevatorClient> elevators;

    // scheduler.Scheduler Context
    private SchedulerState currentState;

    /**
     * The constructor for the scheduler.Scheduler object.
     */
    public Scheduler() {
        super();
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

    public ElevatorClient getClient(DatagramPacket packet) {

        for (ElevatorClient client : elevators) {
            if (client.equals(new ElevatorClient(packet))) {
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
        System.out.println("Scheduler: Moved to " + state.toString());
    }

    /**
     * Helper function for determining if the Elevator is available to server the request.
     *
     * @param elevator The elevator to be checked
     * @param data The request to be served
     *
     * @return True if the elevator can serve the request, false otherwise
     */
    public boolean canServiceRequest(ElevatorClient elevator, FloorData data) {

        // If the elevator is not currently serving a request, it can serve the request
        if (elevator.getStatus().getDirection() == ElevatorStatus.Direction.STATIONARY) {
            return true;
        }

        // If the elevator is going up and the passenger wants to go up, check the floor number
        if (elevator.getStatus().getDirection()==ElevatorStatus.Direction.UP && data.returnDirection()) {
            return elevator.getStatus().getFloor() < data.returnFloorNumber();
        }

        // If the elevator is going down and the passenger wants to go down, check the floor number
        if (elevator.getStatus().getDirection()==ElevatorStatus.Direction.DOWN && !data.returnDirection()) {
            return elevator.getStatus().getFloor() > data.returnFloorNumber();
        }
        return false;
    }

    /**
     * Algorithm for determining which elevator to use for a request.
     *
     * @param data The request information
     *
     * @return The elevator to use
     */
    public ElevatorClient chooseElevator(FloorData data) {

        // Initialize the chosen elevator and the maximum distance
        ElevatorClient chosenElevator = elevators.get(0);
        int maxDistance = 22;

        for (ElevatorClient elevator : elevators) {
            // If the elevator can serve request and is closest, choose that elevator
            int distance = Math.abs(elevator.getStatus().getFloor() - data.returnFloorNumber());
            if (distance <= maxDistance && canServiceRequest(elevator, data)) {
                maxDistance = distance;
                chosenElevator = elevator;
            }
        }
        return chosenElevator;
    }

    /**
     * The thread routine for the scheduler
     */
    public void handleRequests() {

        // Repeat indefinitely
        while (true) {

            // Idle state : wait for request from floor
            FloorData data = currentState.doAction(this, null);
            if (data == null) {
                continue; // Remain in the idle state
            }

            // We have received a request, check the status of it
            if (data.getStatus()) {
                System.out.println("Scheduler: Received a response from Elevator");
                setCurrentState(new SchedulerResponseReceivedState());
            } else {
                System.out.println("Scheduler: Received a new floor request");
                setCurrentState(new SchedulerRequestReceivedState());
            }
            currentState.doAction(this, data);
            setCurrentState(new SchedulerIdleState());
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.handleRequests();
    }
}
