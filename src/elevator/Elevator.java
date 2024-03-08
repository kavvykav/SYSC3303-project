package elevator;

import common.FloorData;
import common.NetworkConstants;
import common.UDPClient;

import java.net.*;
import java.util.ArrayList;

/**
 * Elevator represents the elevator subsystem
 */
public class Elevator extends UDPClient implements Runnable {
    // Number of Elevators
    private static final int NUM_ELEVATORS = 4;

    // elevator.Elevator info
    private ArrayList<Boolean> buttons;
    private ArrayList<Boolean> lamps;
    private Boolean door;
    private Integer currentFloor;

    // elevator.Elevator state
    private ElevatorState currentState = new ElevatorEstablishingConnectionState();

    /**
     * Constructor for the elevator subsystem
     *
     * @param numFloors the number of floors the elevator goes to
     */
    public Elevator(int numFloors, InetAddress address, int port) {
        super(address, port);
        this.lamps = new ArrayList<>(numFloors);
        this.buttons = new ArrayList<>(numFloors);
        for (int i = 0; i < 20; i++) {
            this.lamps.add(false);
            this.buttons.add(false);
        }
        this.door = false;
    }

    /**
     * Elevator goes to the desired floor
     *
     * @param floor the floor the elevator will go to
     */
    public void goTo(int floor) {
        currentFloor = floor;
        buttons.set(floor - 1, false);
        lamps.set(floor - 1, false);
    }

    /**
     * Add a floor to the queue of floors the elevator will go to
     *
     * @param floor the floor to add to the queue
     */
    public void add(int floor) {
        buttons.set(floor - 1, true);
        lamps.set(floor - 1, true);
    }

    /**
     * Getter for the elevator's current position
     *
     * @return the floor the elevator is currently on
     */
    public Integer getCurrentFloor() {
        return currentFloor;
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

    public void serveRequests() {
        // Establish connection
        currentState.doAction(this, null);
        while (true) {
            FloorData receivedData = null;

            setCurrentState(new ElevatorIdleState());
            receivedData = currentState.doAction(this, receivedData);

            setCurrentState(new ElevatorTaskReceivedState());
            currentState.doAction(this, null);

            setCurrentState(new ElevatorMotorRunningState());
            currentState.doAction(this, null);

            setCurrentState(new ElevatorDestinationReachedState());
            currentState.doAction(this, receivedData);
        }
    }

    public void run() {
        serveRequests();
    }

    public static void main(String[] args) {

        InetAddress localHost = NetworkConstants.localHost();
        assert (localHost != null);

        ArrayList<Thread> elevators = new ArrayList<Thread>(NUM_ELEVATORS);
        for (Thread elevator : elevators) {
            elevator = new Thread(new Elevator(22, localHost, NetworkConstants.SCHEDULER_PORT));
        }
        for (Thread elevator : elevators) {
            elevator.start();
        }
    }
}
