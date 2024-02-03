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
            FloorData recievedData = (FloorData) server.receive();
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

        }

    }
}
