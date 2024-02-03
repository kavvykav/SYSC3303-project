import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The Floor class simulates the arrival of passengers, as well as button presses and lamps.
 * In this iteration, the Floor reads from an input file and sends the data to the scheduler.
 *
 * @author Matthew Huybregts 101185221
 * Date: February 3rd, 2024
 */
public class Floor implements Runnable {

    // UDP Client for Floor
    private UDPClient client;

    // Input file for floor subsystem
    private final String filename;

    // The common format used to validate the timestamps
    private static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    /**
     * Constructor for the Floor class
     * @param file The path of the file that the floor should read from
     */
    public Floor(String file) {
        filename = file;
        try {
            client = new UDPClient(InetAddress.getLocalHost(), 5000);
        } catch (UnknownHostException e) {
            System.exit(1);
        }
    }

    /**
     * The main method for the Floor. Here, the Floor reads an input file line by line, translates the data into a
     * custom structure, serializes the data, and sends it to the scheduler.
     */
    public void run() {

        if (client.send("floor") != 0) {
            System.err.println("Failed to send initial message");
            System.exit(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Process each line into a FloorData object
                String[] lineArray = line.split("\\s+");

                String timestamp = lineArray[0];
                int floorNumber, carButton;
                try {
                    LocalTime res = LocalTime.parse(timestamp, timePattern);
                    System.out.println("Validating time: " + res);
                    floorNumber = Integer.parseInt(lineArray[1]);
                    carButton = Integer.parseInt(lineArray[3]);
                } catch (DateTimeParseException | NumberFormatException e) {
                    System.err.println("Invalid data, discarding line");
                    continue;
                }
                boolean direction = lineArray[2].equalsIgnoreCase("up");

                // Send data to Scheduler via UDP
                FloorData data = new FloorData(timestamp, floorNumber, direction, carButton);

                if (client.send(data) != 0) {
                    System.err.println("Failed to send floor data");
                    continue;
                }

                FloorData receivedData = (FloorData) client.receive();
                if (receivedData.equals(data) && receivedData.getStatus()) {
                    System.out.println("Received a response from the scheduler");
                } else {
                    System.err.println("Did not receive a valid response");
                }
            }
        } catch (IOException e) {
            System.err.println("Invalid data, discarding line");
        }
    }
}
