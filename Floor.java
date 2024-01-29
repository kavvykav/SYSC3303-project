import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Floor implements Runnable {

    // Input file for floor subsystem
    private final String filename;

    // The common format for timestamps
    private static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public Floor(String filename) {
        this.filename = filename;
    }

    public void run() {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Process each line into a FloorData object
                String[] lineArray = line.split("\\s+");

                LocalTime timestamp;
                int floorNumber, carButton;

                try {
                    timestamp = LocalTime.parse(lineArray[0], timePattern);
                    floorNumber = Integer.parseInt(lineArray[1]);
                    carButton = Integer.parseInt(lineArray[3]);
                } catch(DateTimeParseException | NumberFormatException e) {
                    System.err.println("Invalid data, discarding line");
                    continue;
                }

                Boolean direction = lineArray[2].equalsIgnoreCase("up");

                // Send data to Scheduler via UDP
                FloorData data = new FloorData(timestamp, floorNumber, direction, carButton);
            }
        } catch (IOException e) {
            System.err.println("Invalid data, discarding line");
        }
    }
}
