import java.net.InetAddress;

public class Scheduler implements Runnable {

    private UDPServer server;
    private InetAddress floorAddress, elevatorAddress;
    private Integer floorPort, elevatorPort;

    /**
     * The constructor for the Scheduler object.
     */
    public Scheduler() {
        server = new UDPServer();
    }

    /**
     * The thread routine for the Scheduler.
     */
    public void run() {
        while (true) {
            Object recievedObject = server.receive();
            if (recievedObject instanceof FloorData) {
                FloorData recievedData = (FloorData) recievedObject;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                // Check status flag to determine where to send the packet
                if (!recievedData.getStatus()) {
                    elevatorAddress = server.getAddress(false);
                    elevatorPort = server.getPort(false);
                    server.send(recievedData, elevatorAddress, elevatorPort);
                } else {
                    floorAddress = server.getAddress(true);
                    floorPort = server.getPort(true);
                    server.send(recievedData, floorAddress, floorPort);
                }
                // This is to establish the initial connections, in this case, we'll
                // just print that the connection has successfully been established
            } else if (recievedObject instanceof String) {
                System.out.println("Successfully established a connection with the " + recievedObject);
            }

        }

    }
}
