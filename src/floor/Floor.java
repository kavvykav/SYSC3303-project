package floor;

import common.FloorRequest;
import common.NetworkConstants;
import common.UDPClient;

import gui.GUI;

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

    // For generating IDs of the requests
    private int idCounter;

    // Array of requests
    private final ArrayList<FloorRequest> requests;

    // Responsible for sending requests to the Scheduler
    private final UDPClient sender;

    // Responsible for processing responses from the Scheduler
    private final UDPClient receiver;

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
        idCounter = 1;
        requests = new ArrayList<>();
    }

    /**
     * Used by the sender to read from the input file and send requests to the scheduler
     */
    public void sendRequests() {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Process each line into a common.FloorData object
                String[] lineArray = line.split("\\s+");

                String timestamp = lineArray[0];
                int floorNumber, carButton, numPassengers;
                LocalTime res = null;
                try {
                    res = LocalTime.parse(timestamp, timePattern);
                    floorNumber = Integer.parseInt(lineArray[1]);
                    carButton = Integer.parseInt(lineArray[3]);
                    numPassengers = Integer.parseInt(lineArray[4]);
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
                        "\n\tDestination Floor: " + carButton +
                        "\n\tNumber of Passengers: " + numPassengers);

                // Send data to scheduler.Scheduler via UDP
                FloorRequest data = new FloorRequest(idCounter, timestamp, floorNumber,
                        direction, carButton, numPassengers);
                idCounter++;

                synchronized (requests) {
                    requests.add(data);
                }

                if (sender.send(data) != 0) {
                    System.err.println("Floor: Failed to send request");
                    continue;
                }

                // Wait a random amount of time between 5 and 15 seconds
                try {
                    Random random = new Random();
                    int randomMillis = random.nextInt(15000 - 5000 + 1) + 5000;
                    Thread.sleep(randomMillis);
                } catch (InterruptedException e) {
                    System.exit(130);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Floor: Invalid data, discarding line");
        }
        System.out.println("Floor: All requests have been sent");
    }

    /**
     * Used by the receiver to receive responses from the Scheduler and process them using the queue
     */
//    public void receiveResponses() {
//        while (true) {
//            Response response = (Response) receiver.receive();
//            FloorData currentRequest = null;
//            boolean boarding = false, leaving = false;
//            synchronized (requests) {
//                for (FloorData request: requests) {
//                    if (request.getId() == response.getId()) {
//                        if (response.getFloor() == request.getFloorNumber()) {
//                            boarding = true;
//                        } else if (response.getFloor() == request.getCarButton()) {
//                            leaving = true;
//                        }
//                        currentRequest = request;
//                        break;
//                    }
//                }
//            }
//            if (currentRequest == null) {
//                continue;
//            }
//
//            Request newRequest;
//            if (boarding) {
//                if (response.getElevatorCapacity() >= currentRequest.getNumPassengers()) {
//                    newRequest = new Request(currentRequest.getId(), currentRequest.getCarButton(),
//                            currentRequest.isDirection(), currentRequest.getNumPassengers());
//                } else {
//                    System.out.println("Floor: Not enough room on elevator, sending another request");
//                    newRequest = new Request(currentRequest.getId(), currentRequest.getFloorNumber(),
//                            currentRequest.isDirection(), 0);
//                }
//            } else if (leaving) {
//
//                newRequest = new Request(currentRequest.getId(), 0,  false, currentRequest.getNumPassengers() * -1);
//
//                // Remove the request since it has been served
//                synchronized (requests) {
//                    requests.remove(currentRequest);
//                }
//            }
//
//            receiver.send(newRequest);
//        }
//    }

    /**
     * The main method for the floor.Floor. Here, the floor reads an input file line by
     * line, translates the data into a
     * custom structure, serializes the data, and sends it to the scheduler.
     */
    public static void main(String[] args) {

        InetAddress localHost = NetworkConstants.localHost();
        assert (localHost != null);

        GUI gui = new GUI();

        Floor floor = new Floor("test_input.txt", localHost, NetworkConstants.SCHEDULER_PORT);
        Thread sender = new Thread(new Sender(floor), "Sender");
        //Thread receiver = new Thread(new Receiver(floor), "Receiver");

        sender.start();
        //receiver.start();
    }
}

/**
 * Sender thread
 */
class Sender implements Runnable {

    Floor floor;

    public Sender(Floor floor) {
        this.floor = floor;
    }

    public void run() {
        floor.sendRequests();
    }
}

/**
 * Receiver thread
 */
//class Receiver implements Runnable {
//
//    Floor floor;
//
//    public Receiver(Floor floor) {
//        this.floor = floor;
//    }
//
//    public void run() {
//        floor.receiveResponses();
//    }
//}
