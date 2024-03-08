package scheduler;

import common.FloorData;
import common.UDPServer;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * This is the main component of the scheduler.Scheduler Subsystem
 */
public class Scheduler extends UDPServer {

    // Port and IP Address
    private ArrayList<ElevatorClient> elevators;

    // scheduler.Scheduler Context
    private SchedulerState currentState;

    /**
     * The constructor for the scheduler.Scheduler object.
     */
    public Scheduler() {
        super();
        elevators = new ArrayList<>(4);
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
    public ElevatorClient getElevator(FloorData data) {
        return elevators.get(0);
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
