package common;

import java.io.*;
import java.net.*;

/**
 * common.UDPClient represents a simple client that communicates over UDP with a specified server.
 * The client is designed to send and receive any type of data using serialization and deserialization methods.
 */
public class UDPClient {

    // For sending and receiving data
    private static final int BUFFER_SIZE = 1024;
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendReceiveSocket;
    private byte[] receivedMsg;

    // Server information
    InetAddress serverAddress;
    int serverPort;

    public UDPClient(InetAddress address, int port) {

        try {
            // This socket will be used to send and receive UDP Datagram packets.
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException e) {
            // e.printStackTrace();
            System.exit(1);
        }
        receivedMsg = new byte[BUFFER_SIZE];
        receivePacket = new DatagramPacket(receivedMsg, receivedMsg.length);

        serverAddress = address;
        serverPort = port;
    }

    /**
     * Send data to the client's server
     *
     * @param data The data to be sent to the server
     * @return 0 if successful, -1 otherwise
     */
    public synchronized int send(Object data) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            byte[] msg = byteArrayOutputStream.toByteArray();
            sendPacket = new DatagramPacket(msg, msg.length, serverAddress, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (sendReceiveSocket) {
            try {
                sendReceiveSocket.send(sendPacket);
            } catch (IOException e) {
                System.err.println("Failed to send data");
                return -1;
            }
        }
        return 0;
    }

    /**
     * Receive data from the clients server
     * NOTE: The client will block indefinitely until it receives data, there is currently no timeout
     *
     * @return The data that was received by the client, null on failure
     */
    public Object receive() {

        synchronized (sendReceiveSocket) {
            try {
                sendReceiveSocket.receive(receivePacket);
            } catch (SocketTimeoutException e) {
                return null;
            } catch (IOException e) {
                System.err.println("Failed to get data from server");
                return null;
            }
        }

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receivedMsg);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to deserialize data");
            return null;
        }
    }
}
