import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Floor implements Runnable {

    // Structures for sending data
    DatagramPacket sendPacket, receivePacket;
    DatagramSocket sendReceiveSocket;

    // Input file for floor subsystem
    private final String filename;

    // The common format used to validate the timestamps
    private static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    // For mapping FloorData object to JSON
    private static final ObjectMapper mapper = new ObjectMapper();

    public Floor(String file) {
        filename = file;
        try {
            // This socket will be used to send and receive UDP Datagram packets.
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException e) {
            //e.printStackTrace();
            System.exit(1);
        }
    }

    public void run() {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Process each line into a FloorData object
                String[] lineArray = line.split("\\s+");

                String timestamp = lineArray[0];
                int floorNumber, carButton;
                try {
                    LocalTime res = LocalTime.parse(timestamp, timePattern);
                    floorNumber = Integer.parseInt(lineArray[1]);
                    carButton = Integer.parseInt(lineArray[3]);
                } catch (DateTimeParseException | NumberFormatException e) {
                    System.err.println("Invalid data, discarding line");
                    continue;
                }

                Boolean direction = lineArray[2].equalsIgnoreCase("up");

                // Send data to Scheduler via UDP
                FloorData data = new FloorData(timestamp, floorNumber, direction, carButton);

                try {
                    byte[] msg = mapper.writeValueAsString(data).getBytes();
                    sendPacket = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), 5000);
                } catch (JsonProcessingException | UnknownHostException e) {
                    //e.printStackTrace();
                    System.err.println("Unable to form message");
                }

                try {
                    sendReceiveSocket.send(sendPacket);
                } catch (IOException e) {
                    //e.printStackTrace();
                    continue;
                }

                byte[] receivedMsg = new byte[1024];
                receivePacket = new DatagramPacket(receivedMsg, receivedMsg.length);

                try {
                    // Block until a datagram is received via sendReceiveSocket
                    sendReceiveSocket.receive(receivePacket);
                } catch(IOException e) {
                    //e.printStackTrace();
                    continue;
                }

                String received = new String(receivedMsg, 0 ,receivePacket.getLength());
                FloorData receivedData = mapper.readValue(received, FloorData.class);

                if (receivedData.equals(data)) {
                    System.out.println("Received a response from the scheduler");
                } else {
                    System.err.println("Did not receive a valid response");
                }
            }
        } catch (IOException e) {
            System.err.println("Invalid data, discarding line");
        }
        sendReceiveSocket.close();
    }
}
