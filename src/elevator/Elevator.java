package elevator;

import common.*;

import static common.NetworkConstants.SCHEDULER_PORT;

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

        status = new ElevatorStatus(id, 1, Direction.STATIONARY, false);
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

    public void addPassenger() {
        elevatorPrint("Boarding a passenger on floor " + status.getFloor());
        status.setEmpty(false);
    }

    public void removePassenger() {
        elevatorPrint("Passenger getting off on floor " + status.getFloor());
        status.setEmpty(true);
    }

    /**
     * Add a floor to the queue of floors the elevator will go to
     *
     * @param floor the first floor to add to the queue
     */
    public void add(int floor) {

        elevatorPrint("Adding floor " + floor);
        if (floor > numFloors || floor < 1) {
            return;
        }

        synchronized (requests) {
            // If there are no requests, add the floor
            if (requests.isEmpty()){
                requests.add(floor);
                return;
            }
            // If the elevator is taking passengers up, put the floor in front of the closest higher floor
            boolean added = false;
            if (status.isGoingUp()) {
                for (int i = 0; i < requests.size(); i++) {
                    if (floor < requests.get(i)) {
                        requests.add(i, floor);
                        added = true;
                        break;
                    }
                }
            } else {
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
            FloorRequest receivedData = currentState.doAction(this, null);

            if (receivedData == null) {
                continue;
            }

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

        int numFloors = 22;
        int numElevators = 4;

        for (int i=1; i<numElevators+1; i++) {
            Elevator elevator = new Elevator(numFloors, localHost, SCHEDULER_PORT, i);
            Thread thread = new Thread(elevator, "Elevator " + i);
            thread.start();
        }
    }
}
