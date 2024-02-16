import java.net.*;
import java.util.ArrayList;

/**
 * Elevator represents the elevator subsystem
 */
public class Elevator extends UDPClient implements Runnable {

    private ArrayList<Boolean> buttons;
    private ArrayList<Boolean> lamps;
    private Boolean door;
    private Integer currentFloor;
    // Elevator context
    private ElevatorState currentState;
    // Elevator states
    private ElevatorEstablishingConnectionState establishingConnectionState;
    private ElevatorIdleState idleState;
    private ElevatorTaskReceivedState taskReceivedState;
    private ElevatorMotorRunningState motorRunningState;
    private ElevatorDestinationReachedState destinationReachedState;

    /**
     * Constructor for the elevator subsystem
     *
     * @param numFloors the number of floors the elevator goes to
     */
    public Elevator(int numFloors, InetAddress address, int port) {
        super(address, port);
        this.lamps = new ArrayList<Boolean>(numFloors);
        this.buttons = new ArrayList<Boolean>(numFloors);
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

    public void run() {
        setCurrentState(establishingConnectionState);
        currentState.doAction(this);
        while (true) {
            FloorData receivedData = (FloorData) super.receive();
            System.out.println("Elevator: Received FloorData from Scheduler");
            try {
                // Simulate arrival
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.exit(130);
            }
            receivedData.setStatus(true);
            if (super.send(receivedData) != 0) {
                System.err.println("Elevator: Failed to respond to Scheduler");
            }
        }
    }
}
