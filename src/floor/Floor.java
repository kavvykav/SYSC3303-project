package floor;

import common.FloorData;
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
import java.util.Random;

/**
 * The Floor class simulates the arrival of passengers, as well as button
 * presses and lamps.
 * In this iteration, the floor.Floor reads from an input file and sends the data to
 * the scheduler.
 *
 * @author Matthew Huybregts 101185221
 *         Date: February 29th, 2024
 */
public class Floor extends UDPClient {

    // Input file for floor subsystem
    private final String filename;

    // The common format used to validate the timestamps
    private static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * Constructor for the floor.Floor class
     * 
     * @param file    The path of the file that the floor should read from
     * @param address Server address to be passed to common.UDPClient
     * @param port    Server port to be passed to common.UDPClient
     */
    public Floor(String file, InetAddress address, int port) {
        super(address, port);
        filename = file;
    }

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
                FloorData data = new FloorData(timestamp, floorNumber, direction, carButton);

                if (send(data) != 0) {
                    System.err.println("Floor: Failed to send floor data");
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
            //e.printStackTrace();
            System.err.println("Floor: Invalid data, discarding line");
        }
        System.out.println("Floor: Finished reading input, exiting program");
        System.exit(0);
    }

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
        floor.sendRequests();
    }
}
