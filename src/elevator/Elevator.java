package elevator;

import common.ElevatorStatus;
import common.FloorData;
import common.NetworkConstants;
import static common.NetworkConstants.SCHEDULER_PORT;
import common.UDPClient;

import java.net.*;
import java.util.ArrayList;

/**
 * Elevator represents the elevator subsystem
 */
public class Elevator extends UDPClient implements Runnable {

    // Elevator ID
    private final int id;

    // The list of requests that need to be served by the elevator
    private final ArrayList<Integer> requests;

    private Boolean door;

    // Elevator state
    private ElevatorState currentState = new ElevatorEstablishingConnectionState();

    // Status of the elevator
    private ElevatorStatus status;

    /**
     * Constructor for the elevator subsystem
     *
     * @param numFloors the number of floors the elevator goes to
     */
    public Elevator(int numFloors, InetAddress address, int port, int id) {

        super(address, port);
        this.id = id;
        status = new ElevatorStatus();

        requests = new ArrayList<>(0);
        door = false;
    }

    public void elevatorPrint(String output) {
        System.out.println("Elevator " + id + ": " + output);
    }

    public ArrayList<Integer> getRequests() {
        return requests;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    /**
     * Open the door
     */
    public void openDoor() {
        door = true;
    }

    /**
     * Close the door
     */
    public void closeDoor() {
        door = false;
    }

    /**
     * Setter for elevator's current state
     * 
     * @param state the state that the elevator will be set to
     */
    public void setCurrentState(ElevatorState state) {
        this.currentState = state;
    }

    /**
     * Getter for elevator's current state
     * 
     * @return the current state of the elevator
     */
    public ElevatorState getCurrentState() {
        return currentState;
    }

    public void sendStatus() {
        send(status);
    }

    /**
     * Add a floor to the queue of floors the elevator will go to
     *
     * @param floor the first floor to add to the queue
     */
    public void add(int floor) {

        System.out.println("Adding floor " + floor);

        synchronized (requests) {
            if (requests.isEmpty()){
                requests.add(floor);
                return;
            }

            boolean added = false;
            if (status.getDirection() == ElevatorStatus.Direction.UP) {
                for (int i=0; i < requests.size(); i++) {
                    if (floor < requests.get(i)) {
                        requests.add(i, floor);
                        added = true;
                        break;
                    }
                }
            }

            if (status.getDirection() == ElevatorStatus.Direction.DOWN) {
                for (int i=0; i < requests.size(); i++) {
                    if (floor > requests.get(i)) {
                        requests.add(i, floor);
                        added = true;
                        break;
                    }
                }
            }
            if (!added) {
                requests.add(floor);
            }
            System.out.println(requests);
        }
    }

    public void serveRequests() {

        // Establish connection
        currentState.doAction(this, null);

        while (true) {

            FloorData receivedData = null;
            setCurrentState(new ElevatorIdleState());
            receivedData = currentState.doAction(this, receivedData);
            if (receivedData == null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    continue;
                }
                continue;
            }

//            setCurrentState(new ElevatorTaskReceivedState());
//            currentState.doAction(this, receivedData);

            setCurrentState(new ElevatorMotorRunningState());
            currentState.doAction(this, receivedData);
        }
    }

    public void run() {
        serveRequests();
    }

    public static void main(String[] args) {

        InetAddress localHost = NetworkConstants.localHost();
        assert (localHost != null);

        Thread car1 = new Thread(new Elevator(22, localHost, SCHEDULER_PORT, 1), "Elevator 1");
        Thread car2 = new Thread(new Elevator(22, localHost, SCHEDULER_PORT, 2), "Elevator 2");
        Thread car3 = new Thread(new Elevator(22, localHost, SCHEDULER_PORT, 3), "Elevator 3");
        Thread car4 = new Thread(new Elevator(22, localHost, SCHEDULER_PORT, 4), "Elevator 4");

        car1.start();
        car2.start();
        car3.start();
        car4.start();
    }
}
