import java.net.*;
import java.util.ArrayList;

/**
 * This class simply stores where a recieve packet is from (i.e floor or
 * elevator),the address of whoever sent the packet, and the port number the
 * packet was
 * sent through
 **/
public class ClientPacketData {
    private String type;
    private InetAddress address;
    private int port;

    /**
     * The constructor for a ClientPacketData object.
     *
     * @param recievePacket the packet we want the port and address of
     * @param is            Floor if it's the floor sending the packet pass in true,
     *                      if it's the elevator, pass in false
     **/
    public ClientPacketData(DatagramPacket recievePacket, boolean isFloor) {
        address = recievePacket.getAddress();
        port = recievePacket.getPort();
        if (isFloor) {
            type = "floor";
        } else {
            type = "elevator";
        }
    }

    /**
     * A getter for the type of client that sent the packet
     *
     * @return the client that sent the packet
     **/
    public String getType() {
        return type;
    }

    /**
     * A getter for the address of the client that sent the packet
     * 
     * @return the address of the client that sent the packet
     **/
    public InetAddress getAddress() {
        return address;
    }

    /**
     * A getter for the port of the client that sent the packet
     *
     * @return the port that the client sent the packet through
     **/
    public int getPort() {
        return port;
    }
}
