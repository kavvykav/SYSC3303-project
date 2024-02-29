package scheduler;

import java.net.*;

/**
 * This class simply stores where a received packet is from (ie floor or
 * elevator),the address of whoever sent the packet, and the port number the
 * packet was sent through
 */
public class SchedulerClient {

    // The type of the client (floor or elevator)
    private final String type;
    // IP address and port number
    private final InetAddress address;
    private final int port;

    /**
     * The constructor for a scheduler.ClientPacketData object.
     *
     * @param receivePacket the packet we want the port and address of
     * @param type       floor or elevator
     */
    public SchedulerClient(DatagramPacket receivePacket, String type) {
        address = receivePacket.getAddress();
        port = receivePacket.getPort();
        this.type = type.toLowerCase();
    }

    /**
     * A getter for the type of client that sent the packet
     *
     * @return the client that sent the packet
     */
    public String getType() {
        return type;
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

        if (!(o instanceof SchedulerClient)) {
            return false;
        }

        // typecast o to scheduler.ClientPacketData so that we can compare data members
        SchedulerClient data = (SchedulerClient) o;
        return type.equalsIgnoreCase(data.type)
                && address.equals(data.address)
                && port == data.port;
    }
}
