package scheduler;

import common.FloorData;
import common.UDPServer;

import java.lang.Math;
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
     * Algorithm for determining which elevator to use for a request.
     *
     * @param data The request information
     *
     * @return The elevator to use
     */
    public ElevatorClient chooseElevator(FloorData data) {
        ElevatorClient chosenElevator = elevators.get(0);
        int maxDistance = 22;

        for (ElevatorClient elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - data.returnFloorNumber());
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

    public boolean canServiceRequest(ElevatorClient elevator, FloorData data) {
        // Case 1: Direction is UP
        if (data.returnDirection() && data.returnFloorNumber() > elevator.getCurrentFloor()) {
            return true;
        }
        // Case 2 : Direction is DOWN
        else if (!data.returnDirection() && data.returnFloorNumber() < elevator.getCurrentFloor()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.handleRequests();
    }
}
