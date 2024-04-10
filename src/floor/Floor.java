package floor;

import common.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;

import static common.NetworkConstants.FLOOR_PORT;

/**
 * The Floor class simulates the arrival of passengers, as well as button
 * presses and lamps.
 * In this iteration, the floor.Floor reads from an input file and sends the data to
 * the scheduler.
 *
 * @author Matthew Huybregts 101185221
 *         Date: February 29th, 2024
 */
public class Floor {

    // Input file for floor subsystem
    private final String filename;

    // The common format used to validate the timestamps
    private static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // To keep track or pending requests
    private final ArrayList<FloorData> passengers;

    // For sending requests
    private final UDPClient sender;

    // For receiving elevator arrival updates from the scheduler
    private final UDPClient receiver;

    // For adding time in between requests
    private final Random rand;

    // To check if the entire file has been read
    private boolean fileRead;

    /**
     * Constructor for the floor.Floor class
     * 
     * @param file    The path of the file that the floor should read from
     * @param address Server address to be passed to common.UDPClient
     * @param port    Server port to be passed to common.UDPClient
     */
    public Floor(String file, InetAddress address, int port) {
        sender = new UDPClient(address, port);
        receiver = new UDPClient(address, port, FLOOR_PORT);
        filename = file;
        passengers = new ArrayList<>();
        rand = new Random();
        fileRead = false;
    }

    /**
     * This function is called by the Sender. It reads through the input file and sends requests to the Scheduler.
     */
    public void sendRequests() {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Process each line into a common.FloorData object
                String[] lineArray = line.split("\\s+");

                String timestamp = lineArray[0];
                int floorNumber, carButton;
                LocalTime res = null;
                try {
                    res = LocalTime.parse(timestamp, timePattern);
                    floorNumber = Integer.parseInt(lineArray[1]);
                    carButton = Integer.parseInt(lineArray[3]);
                } catch (DateTimeParseException | NumberFormatException e) {
                    if (res == null) {
                        System.err.println("Floor: Got an invalid date");
                    }
                    System.err.println("Floor: Invalid data, discarding line");
                    continue;
                }
                boolean direction = lineArray[2].equalsIgnoreCase("up");

                System.out.println("Floor: Reading data" +
                        "\n\tTime: " + timestamp +
                        "\n\tCurrent Floor: " + floorNumber +
                        "\n\tDirection: " + lineArray[2] +
                        "\n\tDestination Floor: " + carButton);

                // Send data to scheduler.Scheduler via UDP
                try {
                    FloorData data = new FloorData(timestamp, floorNumber, direction, carButton);
                    synchronized (passengers) {
                        passengers.add(data);
                    }
                } catch(IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }

                FloorRequest request = new FloorRequest(floorNumber, 0, direction);
                if (sender.send(request) != 0) {
                    System.err.println("Floor: Failed to send request");
                    continue;
                }

                // Wait a random amount of time between 5 and 15 seconds
                try {
                    int randomMillis = rand.nextInt(15000 - 5000 + 1) + 5000;
                    Thread.sleep(randomMillis);
                } catch (InterruptedException e) {
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Floor: Invalid data, discarding line");
        }
        fileRead = true;
        System.out.println("Floor: Finished reading input");
    }

    /**
     * This function is called by the Receiver. It waits for elevator arrivals, and simulates a passenger boarding
     * by sending a request to the Scheduler.
     */
    public void receiveResponses(){

        while (true) {
            // Wait for an elevator arrival
            ElevatorStatus update = (ElevatorStatus) receiver.receive();
            FloorRequest floorRequest = null;
            PassengerRequest passengerRequest = null;
            FloorData servedPassenger = null;
            boolean resend = false;
            // Iterate over our list of waiting passengers
            synchronized (passengers) {
                for (FloorData passenger: passengers) {
                    boolean direction = passenger.getDirection();
                    int currFloor = passenger.getFloorNumber();
                    int destFloor = passenger.getCarButton();
                    if (currFloor == update.getFloor() && direction == update.isGoingUp()) {
                        // The elevator has arrived at the floor of a passenger
                        // Check if there is space on the elevator
                        if (!update.isEmpty()) {
                            // Send a new request at the passenger floor for a different elevator
                            floorRequest = new FloorRequest(currFloor, update.getId() * -1, direction);
                            resend = true;
                            break;
                        }
                        // Board the elevator
                        passenger.setElevator(update.getId());
                        passengerRequest = new PassengerRequest(true, update.getId());
                        floorRequest = new FloorRequest(destFloor, update.getId(), direction);

                    } else if (destFloor== update.getFloor() && passenger.getElevator() == update.getId()) {
                        // The elevator has arrived at the destination floor of a passengers
                        // Get off the elevator
                        passengerRequest = new PassengerRequest(false, update.getId());
                        servedPassenger = passenger;
                        break;
                    }
                }
            }
            // Send a passenger to request to the scheduler (Boarding or Getting Off)
            if (passengerRequest != null) {
                receiver.send(passengerRequest);
            }
            // Send a request for a floor to the scheduler
            if (floorRequest != null) {
                receiver.send(floorRequest);
                if (resend) {
                    System.out.println("Floor: Elevator not empty, sending another request at " +
                            floorRequest.getFloor());
                }
            }
            // If a passenger has been served, remove them from our list
            if (servedPassenger != null) {
                synchronized (passengers) {
                    passengers.remove(servedPassenger);
                }
            }
        }
    }

    /**
     * Check if the input file has been read
     * @return true if the input file has been read, false otherwise
     */
    public boolean isFileRead() {
        return fileRead;
    }

    /**
     * The main method for the floor.Floor. Here, the floor reads an input file line by
     * line, translates the data into a
     * custom structure, serializes the data, and sends it to the scheduler.
     */
    public static void main(String[] args) {

        InetAddress localHost = NetworkConstants.localHost();
        assert (localHost != null);

        Floor floor = new Floor("test_input.txt", localHost, NetworkConstants.SCHEDULER_PORT);
        Thread sender = new Thread(new Sender(floor), "Sender");
        Thread receiver = new Thread(new Receiver(floor), "Receiver");
        sender.start();
        receiver.start();
    }
}

/**
 * The Sender thread
 */
class Sender implements Runnable {

    // Floor reference
    private final Floor floor;

    public Sender(Floor floor) {
        this.floor = floor;
    }

    public void run() {
        floor.sendRequests();
    }
}

/**
 * The Receiver thread
 */
class Receiver implements Runnable {

    // Floor reference
    private final Floor floor;

    public Receiver(Floor floor) {
        this.floor = floor;
    }

    public void run() {
        floor.receiveResponses();
    }
}
