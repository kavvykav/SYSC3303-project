package scheduler;

import common.ElevatorStatus;
import java.net.*;

/**
 * This class simply stores where a received packet is from (ie floor or
 * elevator),the address of whoever sent the packet, and the port number the
 * packet was sent through
 */
public class ElevatorClient {

    // IP address and port number of the elevator
    private final InetAddress address;
    private final int port;

    // Status of the elevator
    private ElevatorStatus status;

    /**
     * The constructor for a scheduler.ClientPacketData object.
     *
     * @param address The IP address of the elevator
     * @param port The port number of the elevator
     * @param status The status of the elevator
     */
    public ElevatorClient(InetAddress address, int port, ElevatorStatus status) {
        this.address = address;
        this.port = port;
        this.status = status;
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

    public ElevatorStatus getStatus() {
        return status;
    }

    public void setStatus(ElevatorStatus newStatus) {
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

        // typecast o to Elevator so that we can compare data members
        ElevatorClient data = (ElevatorClient) o;
        return address.equals(data.address)
                && port == data.port
                && status.equals(data.status);
    }
}
