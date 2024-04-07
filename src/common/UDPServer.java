package common;

import java.net.*;
import java.io.*;

/**
 * This class is simply a handler for the scheduler.Scheduler, which is supposed
 * to send
 * and receive events in this particular order : floor->scheduler.Scheduler,
 * scheduler.Scheduler->
 * elevator.Elevator, elevator.Elevator->scheduler.Scheduler,
 * scheduler.Scheduler->floor
 */
public class UDPServer {

    private static final int BUFFER_SIZE = 1024;
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendSocket, receiveSocket;
    private byte[] receivedMsg;

    /**
     * The constructor for the common.UDPServer object.
     * 
     **/
    public UDPServer(int port) {
        try {
            receiveSocket = new DatagramSocket(port);
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            // e.printStackTrace();
            System.exit(1);
        }
        receivedMsg = new byte[BUFFER_SIZE];
        receivePacket = new DatagramPacket(receivedMsg, receivedMsg.length);
    }

    public DatagramPacket getReceivePacket() {
        return receivePacket;
    }

    /**
     * A method for the server to send a DatagramPacket to a specific client.
     *
     * @param clientAddress the address of the client we want to send to
     * @param clientPort    the port of the client we want to send to
     *
     * @return 0 if successful, -1 otherwise
     */
    public int send(Object data, InetAddress clientAddress, int clientPort) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            byte[] msg = byteArrayOutputStream.toByteArray();
            sendPacket = new DatagramPacket(msg, msg.length, clientAddress, clientPort);
        } catch (IOException e) {
            // e.printStackTrace();
            System.exit(1);
        }
        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            System.err.println("Failed to send data");
            return -1;
        }
        return 0;
    }

    /**
     * A method to receive a packet from a client.
     * 
     * @return the object that was received from the server
     */
    public Object receive() {

        try {
            receiveSocket.receive(receivePacket);
        } catch (IOException e) {
            // e.printStackTrace();
            return null;
        }
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivedMsg);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            return null;
        }
    }
}
