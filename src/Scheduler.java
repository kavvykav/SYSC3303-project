import java.util.ArrayList;

public class Scheduler extends UDPServer implements Runnable {

    private ArrayList<ClientPacketData> clients;

    /**
     * The constructor for the Scheduler object.
     */
    public Scheduler() {
        super();
        clients = new ArrayList<>(2);
    }

    private ClientPacketData getClient(String type) {
        for (ClientPacketData client : clients) {
            if (client.getType().equalsIgnoreCase(type)) {
                return client;
            }
        }
        return null;
    }

    /**
     * The thread routine for the Scheduler.
     */
    public void run() {
        while (true) {
            Object receivedObject = receive();
            if (receivedObject instanceof FloorData) {

                // Check status flag to determine where to send the packet
                FloorData receivedData = (FloorData) receivedObject;
                String type = receivedData.getStatus() ? "Floor" : "Elevator";
                ClientPacketData client = getClient(type.toLowerCase());
                if (client == null) {
                    System.err.println("Scheduler: Message from unknown " + type);
                    continue;
                }
                System.out.println("Scheduler: Got FloorData from" + type);
                if (send(receivedData, client.getAddress(), client.getPort()) != 0) {
                    System.err.println("Scheduler: Failed to send FloorData to " + type);
                }
            } else if (receivedObject instanceof String) {

                // This is to establish the initial connections
                String type = (String) receivedObject;
                if (!type.equalsIgnoreCase("floor") && !type.equalsIgnoreCase("elevator")) {
                    System.err.println("Scheduler: Invalid client type");
                }
                ClientPacketData client = new ClientPacketData(getReceivePacket(), type.toLowerCase());

                System.out.println("Scheduler: Successfully established a connection with the " + receivedObject);
                if (!clients.contains(client)) {
                    clients.add(client);
                }
            }
        }
    }
}
