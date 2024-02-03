import java.util.ArrayList;
import java.net.*;
import java.io.*;

/**
 * This class is simply a handler for the Scheduler, which is supposed to send
 * and recieve events in this particular order : floor->Scheduler, Scheduler->
 * Elevator, Elevator->Scheduler, Scheduler->floor
 **/
public class UDPServer {

    private static final int MESSAGE_SIZE = 1024;
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendSocket, receiveSocket;

    // For serializing and de-serializing FloorData objects
    private ByteArrayOutputStream byteStream;
    private ObjectOutputStream stream;

    // For storing addresses and port numbers
    private ArrayList<InetAddress> clientAddresses;
    private ArrayList<Integer> clientPorts;

    /**
     * The constructor for the UDPServer object.
     * 
     **/
    public UDPServer() {
        try {
            receiveSocket = new DatagramSocket(5000);
            sendSocket = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
        byteStream = new ByteArrayOutputStream();
        try {
            stream = new ObjectOutputStream(byteStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        clientAddresses = new ArrayList<InetAddress>();
        clientPorts = new ArrayList<Integer>();
    }

    /**
     * A method for the server to send a DatagramPacket to a specific client.
     *
     * @param clientAddress the address of the client we want to send to
     * @param clientPort    the port of the client we want to send to
     *
     * @return
     **/
    public void send(Object message, InetAddress clientAddress, int clientPort) {
        byte[] sendMessage = new byte[MESSAGE_SIZE];
        try {
            stream.writeObject(message);
            stream.flush();
            sendMessage = byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        sendPacket = new DatagramPacket(sendMessage, sendMessage.length, clientAddress, clientPort);
        try {
            sendSocket.send(sendPacket);
            System.out.println("Sent a packet to " + clientAddress + " on port " + clientPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * A method to recieve a packet from a client.
     * 
     * @return the object that was recieved from the server
     **/
    public Object receive() {
        byte[] recievedData = new byte[MESSAGE_SIZE];
        receivePacket = new DatagramPacket(recievedData, recievedData.length);
        try {
            receiveSocket.receive(receivePacket);

            // If this is the first packet from a client, store the address and port.
            if (clientAddresses.size() < 2) {
                clientAddresses.add(receivePacket.getAddress());
            }
            if (clientPorts.size() < 2) {
                clientPorts.add(receivePacket.getPort());
            }
            System.out.println("Recieved a packet from host " + receivePacket.getAddress()
                    + " on port " + receivePacket.getPort());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recievedData);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This returns the address for the client that is specified.
     *
     * @param isFloor : pass in true if you want the address of the floor, false if
     *                you want the address of the Elevator
     *
     * @return the address of the specified client
     **/
    public InetAddress getAddress(boolean isFloor) {
        if (isFloor) {
            return clientAddresses.get(0);
        }
        return clientAddresses.get(1);
    }

    /**
     * This returns the port for the client that is specified.
     *
     * @param isFloor : pass in true if you want the port of the floor, false if you
     *                want the port of the Elevator
     *
     * @return the address of the specified client
     **/
    public Integer getPort(boolean isFloor) {
        if (isFloor) {
            return clientPorts.get(0);
        }
        return clientPorts.get(1);
    }

}
