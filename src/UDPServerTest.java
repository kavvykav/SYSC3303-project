import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static org.junit.Assert.assertEquals;

public class UDPServerTest {
    private UDPServer server;
    private Thread serverThread;

    @Before
    public void setup() {
        server = new UDPServer();

        // Start the server in a separate thread
        serverThread = new Thread(() -> server.receive());
        serverThread.start();
    }

    @After
    public void teardown() {
        serverThread.interrupt();
    }

    @Test
    public void testServer() throws Exception {
        // Create a UDP client to send a message to the server
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = "Hello, server!".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8888);
        clientSocket.send(sendPacket);

        // Wait for the server to receive the message
        Thread.sleep(1000); // Wait for 1 second

        // Check if the server received the message
        assertEquals("Hello, server!", server.receive());

        // Close the client socket
        clientSocket.close();
    }
}