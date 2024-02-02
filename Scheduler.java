import java.net.InetAddress;

public class Scheduler implements Runnable {

    private UDPServer server;

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
        try {
            InetAddress address = InetAddress.getLocalHost();
            String msg = server.receive(address, 5000);
            server.send(msg, address, 5000);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
