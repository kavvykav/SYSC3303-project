package scheduler;

import common.Direction;
import common.ElevatorStatus;
import java.net.*;

/**
 * This class stores information about an Elevator to help the Scheduler keep track of all the connected elevators.
 * It contains the IP address and port of the elevator, as well as the status information of it.
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
     * @param id The ID of the elevator
     */
    public ElevatorClient(InetAddress address, int port, int id) {

        this.address = address;
        this.port = port;

        status = new ElevatorStatus(id, 1, Direction.STATIONARY, false);
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
     * Getter method for the status of the Elevator
     * @return the status of the elevator
     */
    public ElevatorStatus getStatus() {
        return status;
    }

    /**
     * Setter method for the status of the Elevator
     * @param newStatus the new status of the Elevator
     */
    public void setStatus(ElevatorStatus newStatus) {
        status = newStatus;
    }

    /**
     * The equals method for the scheduler.ClientPacketData class.
     *
     * @param o the ElevatorClient object we are comparing
     *
     * @return true if the objects are equal, false otherwise
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
