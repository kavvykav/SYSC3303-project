import java.net.*;

/**
 * This class is simply a handler for the Scheduler, which is supposed to send
 * and recieve events in this particular order : floor->Scheduler, Scheduler->
 * Elevator, Elevator->Scheduler, Scheduler->floor
 **/
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
     * A method for the server to send a DatagramPacket to a specific client.
     *
     * @param clientAddress the address of the client we want to send to
     * @param clientPort    the port of the client we want to send to
     *
     * @return
     **/
    public void send(String message, InetAddress clientAddress, int clientPort) {
        try {
            byte[] sendData = new byte[MESSAGE_SIZE];
            sendData = message.getBytes();
            // Had to create the send socket locally since it's dependent on which client we
            // want to send to
            sendSocket = new DatagramSocket(clientPort, clientAddress);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            sendSocket.send(sendPacket);
            System.out.println("Sent data to " + clientAddress + " on Port " + clientPort);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * A method to recieve a packet from a client.
     * 
     * @param address the address of the client we want to recieve from
     * @param port    the port we want to recieve from
     *
     * @return the String object that was recieved from the server
     **/
    public String receive(InetAddress address, int port) {
        String data = null;
        try {
            byte[] recievedMessage = new byte[MESSAGE_SIZE];
            DatagramPacket recievePacket = new DatagramPacket(recievedMessage, recievedMessage.length, address, port);
            receiveSocket.receive(recievePacket);
            recievedMessage = receivePacket.getData();
            int packetLength = receivePacket.getLength();

            data = new String(recievedMessage, 0, packetLength);
            System.out.println("Recieved " + data + " from host " + address + " on port " + port);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return data;
    }
}
