import java.util.ArrayList;

/**
 * This is the main component of the Scheduler Subsystem
 */
public class Scheduler extends UDPServer implements Runnable {

    private FloorData data;
    // Elevator and Floor Ports and IP Addresses
    private ArrayList<ClientPacketData> clients;

    // Scheduler Context
    private SchedulerState currentState;

    // Scheduler states
    private SchedulerEstablishConnectionState establishConnectionState;
    private SchedulerIdleState idleState;
    private SchedulerRequestReceivedState requestReceivedState;
    private SchedulerWaitState waitState;
    private SchedulerResponseReceivedState responseReceivedState;

    /**
     * The constructor for the Scheduler object.
     */
    public Scheduler() {
        super();
        clients = new ArrayList<>(2);
        establishConnectionState = new SchedulerEstablishConnectionState(this);
        idleState = new SchedulerIdleState(this);
        requestReceivedState = new SchedulerRequestReceivedState(this);
        waitState = new SchedulerWaitState(this);
        responseReceivedState = new SchedulerResponseReceivedState(this);

        // Initialize to idleState
        currentState = establishConnectionState;
    }

    /**
     * Gets information about the specified client.
     *
     * @param type: elevator or floor
     * @return a ClientPacketData object containing the IP address and the Port.
     */
    public ClientPacketData getClient(String type) {
        for (ClientPacketData client : clients) {
            if (client.getType().equalsIgnoreCase(type)) {
                return client;
            }
        }
        return null;
    }

    public void setCurrentState(SchedulerState state) {
        currentState = state;
        System.out.println("Scheduler : Moved to " + state.toString());
    }

    public SchedulerState getCurrentState() {
        return currentState;
    }
    public boolean haveFloorData(){
        if (data != null){
            return true;
        }
        return false;
    }

    /**
     * Adds a client to the ArrayList if it's not already there
     *
     * @param client : the client we want to add
     */
    public void addClient(ClientPacketData client) {
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    /**
     * The thread routine for the Scheduler.
     */
    public void run() {
        // State to establish initial connection, it only happens once so leave
        // outside of loop
        currentState.doAction();
        while (true) {
            // Idle state : wait for request from floor
            setCurrentState(idleState);
            currentState.doAction();

            // Retreive data received in the Idle state, switch to the request
            // received state and send data to the elevator
            data = idleState.getReceivedData();
            setCurrentState(requestReceivedState);
            requestReceivedState.chooseDataToSend(data);
            currentState.doAction();

            // After the request is sent to the floor, receive response back
            // from the floor
            setCurrentState(waitState);
            currentState.doAction();

            // Retreive data received in the Wait state, and send it back to the
            // floor.
            data = waitState.getReceivedData(); // Yes, I know it's the same data, but it won't be in future iterations
            setCurrentState(responseReceivedState);
            responseReceivedState.chooseDataToSend(data);
            currentState.doAction();
        }
    }
}
