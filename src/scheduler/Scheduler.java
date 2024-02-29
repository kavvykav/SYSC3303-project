package scheduler;

import common.FloorData;
import common.UDPServer;

import java.util.ArrayList;

/**
 * This is the main component of the scheduler.Scheduler Subsystem
 */
public class Scheduler extends UDPServer implements Runnable {

    // elevator.Elevator and floor.Floor Ports and IP Addresses
    private ArrayList<SchedulerClient> clients;

    // scheduler.Scheduler Context
    private SchedulerState currentState;

    /**
     * The constructor for the scheduler.Scheduler object.
     */
    public Scheduler() {
        super();
        clients = new ArrayList<>(2);
    }

    /**
     * Gets information about the specified client.
     *
     * @param type: elevator or floor
     * @return a scheduler.ClientPacketData object containing the IP address and the Port.
     */
    public SchedulerClient getClient(String type) {
        for (SchedulerClient client : clients) {
            if (client.getType().equalsIgnoreCase(type)) {
                return client;
            }
        }
        return null;
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
     * Returns the current state.
     *
     * @return the current state of the scheduler.Scheduler
     */
    public SchedulerState getCurrentState() {
        return currentState;
    }

    /**
     * Adds a client to the ArrayList if it's not already there
     *
     * @param client : the client we want to add
     */
    public void addClient(SchedulerClient client) {
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    /**
     * The thread routine for the scheduler
     */
    public void run() {
        // Establish initial connections
        setCurrentState(new SchedulerEstablishConnectionState());
        currentState.doAction(this, null);
        // Repeat indefinitely
        while (true) {

            // Idle state : wait for request from floor
            setCurrentState(new SchedulerIdleState());
            FloorData data = currentState.doAction(this, null);

            // Retrieve data received in the Idle state, switch to the request
            setCurrentState(new SchedulerRequestReceivedState());
            currentState.doAction(this, data);

            // After the request is sent to the floor, wait for response
            setCurrentState(new SchedulerWaitState());
            data = currentState.doAction(this, null);

            // Retrieve data received in the Wait state, and send it back to the floor.
            setCurrentState(new SchedulerResponseReceivedState());
            currentState.doAction(this, data);
        }
    }
}
