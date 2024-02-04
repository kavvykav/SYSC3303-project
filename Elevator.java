import java.net.*;
import java.util.ArrayList;

/**
 * Elevator represents the elevator subsystem
 */
public class Elevator implements Runnable {

    private ArrayList<ElevatorButton> buttons;
    private ArrayList<ElevatorLamp> lamps;
    private Motor motor;
    private Door door;
    private Integer currentFloor;
    private UDPClient client;

    /**
     * Constructor for the elevator subsystem
     *
     * @param numFloors the number of floors the elevator goes to
     */
    public Elevator(int numFloors) {
        this.motor = new Motor();
        this.lamps = new ArrayList<ElevatorLamp>(numFloors);
        this.buttons = new ArrayList<ElevatorButton>(numFloors);
        for (int i = 0; i < 20; i++) {
            this.lamps.add(new ElevatorLamp());
            this.buttons.add(new ElevatorButton());
        }
        this.door = new Door();
        try {
            client = new UDPClient(InetAddress.getLocalHost(), 5000);
        } catch (UnknownHostException e) {
            System.exit(1);
        }
    }

    /**
     * Elevator goes to the desired floor
     *
     * @param floor the floor the elevator will go to
     */
    public void goTo(int floor) {
        currentFloor = floor;
        buttons.get(floor - 1).arrive();
        lamps.get(floor - 1).turnOff();
    }

    /**
     * Add a floor to the queue of floors the elevator will go to
     *
     * @param floor the floor to add to the queue
     */
    public void add(int floor) {
        buttons.get(floor - 1).press();
        lamps.get(floor - 1).turnOn();
    }

    /**
     * Getter for the elevator's current position
     *
     * @return the floor the elevator is currently on
     */
    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void run() {

        if (client.send("elevator") != 0) {
            System.err.println("Elevator: Failed to send initial message");
            System.exit(1);
        }
        while (true) {
            FloorData receivedData = (FloorData) client.receive();
            System.out.println("Elevator: Received FloorData from Scheduler");
            try {
                // Simulate arrival
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.exit(130);
            }
            receivedData.setStatus(true);
            if (client.send(receivedData) != 0) {
                System.err.println("Elevator: Failed to respond to Scheduler");
            }
        }
    }
}
