package scheduler;

import common.FloorData;
import java.net.*;

/**
 * This class simply stores where a received packet is from (ie floor or
 * elevator),the address of whoever sent the packet, and the port number the
 * packet was sent through
 */
public class ElevatorClient {

    // For describing the status of the elevator
    public enum Status {
        STATIONARY,
        DOWN,
        UP
    }

    // IP address and port number of the elevator
    private final InetAddress address;
    private final int port;

    // Status of the elevator
    private int id;
    private int currentFloor;
    private Status status;

    private FloorData request;

    /**
     * The constructor for a scheduler.ClientPacketData object.
     *
     * @param receivePacket the packet we want the port and address of
     */
    public ElevatorClient(DatagramPacket receivePacket, int id) {

        address = receivePacket.getAddress();
        port = receivePacket.getPort();

        this.id = id;
        currentFloor = 0;
        status = Status.STATIONARY;
        request = null;
    }

    /**
     * A getter for the address of the client that sent the packet
     *
     * @return the address of the client that sent the packet
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * A getter for the port of the client that sent the packet
     *
     * @return the port that the client sent the packet through
     */
    public int getPort() {
        return port;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        currentFloor = floor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus) {
        status = newStatus;
    }

    /**
     * The equals method for the scheduler.ClientPacketData class.
     *
     * @param o The scheduler.ClientPacketData object we are comparing
     *
     * @return true if scheduler.ClientPacketData objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof ElevatorClient)) {
            return false;
        }

        // typecast o to scheduler.ClientPacketData so that we can compare data members
        ElevatorClient data = (ElevatorClient) o;
        return address.equals(data.address)
                && port == data.port;
    }
}
