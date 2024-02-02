import java.net.*;
import java.io.*;

public class UDPServer {

    private static final int MESSAGE_SIZE = 1024;
    DatagramPacket sendPacket, receivePacket;
    DatagramSocket sendSocket, receiveSocket;

    /**
     * The constructor for the UDPServer object.
     * 
     **/
    public UDPServer() {
        try {
            receiveSocket = new DatagramSocket(5000);

        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * A method for the server to recieve and pass on the data.
     **/
    public void receiveAndSend() {

        byte data[] = new byte[MESSAGE_SIZE];
        receivePacket = new DatagramPacket(data, data.length);
        System.out.println("Server: Waiting for Packet.\n");

        // Block until a datagram packet is received from receiveSocket.
        try {
            System.out.println("Waiting..."); // so we know we're waiting
            receiveSocket.receive(receivePacket);
        } catch (IOException e) {
            System.out.print("IO Exception: likely:");
            System.out.println("Receive Socket Timed Out.\n" + e);
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Server: Packet received:");
        System.out.println("From host: " + receivePacket.getAddress());
        System.out.println("Host port: " + receivePacket.getPort());
        int len = receivePacket.getLength();
        System.out.println("Length: " + len);
        System.out.print("Containing: ");

        String received = new String(data, 0, len);
        System.out.println(received + "\n");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        sendPacket = new DatagramPacket(data, receivePacket.getLength(),
                receivePacket.getAddress(), receivePacket.getPort());

        System.out.println("Server: Sending packet:");
        System.out.println("To host: " + sendPacket.getAddress());
        System.out.println("Destination host port: " + sendPacket.getPort());
        len = sendPacket.getLength();
        System.out.println("Length: " + len);
        System.out.print("Containing: ");
        System.out.println(new String(sendPacket.getData(), 0, len));

        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Server: packet sent");

        // We're finished, so close the sockets.
        sendSocket.close();
        receiveSocket.close();
    }
}
