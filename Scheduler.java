import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;

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
        server.receiveAndSend();
    }
}
