package elevator;

import common.ElevatorStatus;
import common.FloorData;
import common.NetworkConstants;
import static common.NetworkConstants.SCHEDULER_PORT;
import common.UDPClient;

import java.net.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Elevator represents the elevator subsystem
 */
public class Elevator extends UDPClient implements Runnable {

    // The list of requests that need to be served by the elevator
    private final ArrayList<Integer> requests;

    private Boolean door;

    // Elevator state
    private ElevatorState currentState = new ElevatorEstablishingConnectionState();

    // Status of the elevator
    private final ElevatorStatus status;

    // The number of floors in the elevator
    private final int numFloors;

    // For moving the elevator
    private Thread motor;

    // For ensuring that deadlines are met
    private Thread timer;

    /**
     * Constructor for the elevator subsystem
     *
     * @param numFloors the number of floors the elevator goes to
     */
    public Elevator(int numFloors, InetAddress address, int port, int id) {
        super(address, port);
        this.numFloors = numFloors;

        status = new ElevatorStatus(id, 1, ElevatorStatus.Direction.STATIONARY);
        requests = new ArrayList<>(0);
        door = false;
    }

    /**
     * Helper method for distinguishing the output of an elevator
     *
     * @param output The message to be printed by the elevator
     */
    public void elevatorPrint(String output) {
        System.out.println("Elevator " + status.getId() + ": " + output);
    }


    /**
     * Returns if the elevator should stop based on its request and current floor
     *
     * @return true if the elevator should stop, false otherwise
     */
    public boolean shouldStop() {
        synchronized (requests) {
            return requests.get(0).equals(getStatus().getFloor());
        }
    }

    /**
     * Removes and returns the request at the front of the list
     *
     * @return The floor number request that has been serviced
     */
    public Integer updateRequests() {
        synchronized (requests) {
            return requests.remove(0);
        }
    }

    /**
     * Returns the current floor request the elevator is serving
     *
     * @return The floor number the elevator is currently going to
     */
    public Integer getCurrentRequest() {
        synchronized (requests) {
            return requests.get(0);
        }
    }

    /**
     * Returns the number of request the elevator has
     *
     * @return The size of the requests list
     */
    public int getNumRequests() {
        synchronized (requests) {
            return requests.size();
        }
    }

    /**
     * Returns the status of the elevator
     *
     * @return The ElevatorStatus object
     */
    public ElevatorStatus getStatus() {
        return status;
    }

    /**
     * Returns the status of the door. For testing purposes only
     *
     * @return true if door open, false if door is closed
     */
    public Boolean getDoorStatus(){ return door;}

    /**
     * Open the door
     */
    public void openDoor() {
        door = true;
    }

    /**
     * Close the door. If the elevator door is stuck throw an exception.
     */
    public void closeDoor() throws Exception {
        Random rand = new Random();
        if (rand.nextInt(5) == 2) {
            throw new Exception();
        }
        door = false;
    }

    /**
     * Force closes the door if it gets stuck.
     */
    public void forceCloseDoor() {
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

    /**
     * Start a motor thread
     */
    public void startMotor() {
        motor = new Thread(new Motor(this, serverAddress, serverPort), "Motor");
        motor.start();
        elevatorPrint("Motor is running");
    }

    /**
     * Start a timer thread
     *
     * @param time Amount of time in seconds
     */
    public void startTimer(int time) {
        timer = new Thread(new Timer(this, time), "Timer: " + time + " seconds");
        timer.start();
    }

    /**
     * Interrupt the timer thread
     */
    public void stopTimer() {
        timer.interrupt();
    }

    /**
     * get the timer status. For testing purposes only
     *
     * @return the timer object
     */
    public Thread getTimerStatus(){return timer;}

    /**
     * get the motor status. For testing purposes only
     *
     * @return the motor object
     */
    public Thread getMotorStatus(){return motor;}

    /**
     * Interrupt the motor thread
     */
    public void timeout() {
        motor.interrupt();
    }

    /**
     * Add a floor to the queue of floors the elevator will go to
     *
     * @param floor the first floor to add to the queue
     */
    public void add(int floor) {

        elevatorPrint("Adding floor " + floor);
        synchronized (requests) {
            // If there are no requests, add the floor
            if (requests.isEmpty()){
                requests.add(floor);
                return;
            }
            // If the direction of the elevator is UP, put the floor in front of the closest higher floor
            boolean added = false;
            if (status.getDirection() == ElevatorStatus.Direction.UP) {
                for (int i = 0; i < requests.size(); i++) {
                    if (floor < requests.get(i)) {
                        requests.add(i, floor);
                        added = true;
                        break;
                    }
                }
            }
            // If the direction of the elevator is DOWN, put the floor in front of the closest lower floor
            if (status.getDirection() == ElevatorStatus.Direction.DOWN) {
                for (int i=0; i < requests.size(); i++) {
                    if (floor > requests.get(i)) {
                        requests.add(i, floor);
                        added = true;
                        break;
                    }
                }
            }
            // If floor was not inserted within the list, add it to the end
            if (!added && !requests.contains(floor)) {
                requests.add(floor);
            }
        }
        elevatorPrint(requests.toString());
    }

    /**
     * The main sequence for an Elevator instance. Listens for requests and moves accordingly.
     */
    public void serveRequests() {

        // Establish connection
        currentState.doAction(this, null);

        while (true) {
            // Wait to receive a request
            setCurrentState(new ElevatorIdleState());
            FloorData receivedData = currentState.doAction(this, null);

            // Upon receiving request, start motor and return to idle state
            setCurrentState(new ElevatorRequestReceivedState());
            currentState.doAction(this, receivedData);
        }
    }

    /**
     * Run method for the Elevator thread
     */
    public void run() {
        serveRequests();
    }

    /**
     * Creates and runs four Elevator instances
     * @param args Not used at the moment
     */
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
