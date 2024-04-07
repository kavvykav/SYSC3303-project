package gui;

import java.net.InetAddress;

import common.Direction;
import common.ElevatorStatus;
import common.UDPClient;
import common.NetworkConstants;
import static common.NetworkConstants.GUI_PORT;
import static common.NetworkConstants.SCHEDULER_PORT;

public class GUIHandler extends UDPClient implements Runnable {
    private static GUI gui;

    /**
     * The constructor for the GUIHandler class.
     *
     * @param address: the IP address that is being used.
     * @param port:    the port the handler is listening on.
     */
    public GUIHandler(InetAddress address, int port, GUI gui) {
        super(address, SCHEDULER_PORT, port);
        this.gui = gui;
    }

    /**
     * The thread routine for the GUIHandler.
     */
    public void run() {
        while (true) {
            System.out.println("Start of thread routine");
            Object receivedData = receive();
            System.out.println("Object received");
            synchronized (receivedData) {
                if (receivedData instanceof ElevatorStatus) {
                    ElevatorStatus status = (ElevatorStatus) receivedData;
                    int elevator = status.getId();
                    System.out.println("Elevator ID: " + elevator);
                    if (status.getDirection() == Direction.STUCK) {
                        gui.appendToErrorLog(elevator,
                                "Currently stuck somewhere. Elevator will be out of service indefinitely.");
                    } else if (status.getDirection() == Direction.DOOR_STUCK) {
                        gui.appendToErrorLog(elevator, "Door is stuck. Elevator will be temporarily out of service.");
                    } else {
                        Integer floor = Integer.valueOf(status.getFloor());
                        gui.updateLocationField(elevator, floor);
                    }
                }
                else {
                    System.err.println("Did not receive the correct type of information");
                }
            }
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        for (int i = 0; i < 4; i++) {
            GUIHandler handler = new GUIHandler(NetworkConstants.localHost(), GUI_PORT, gui);
            Thread thread = new Thread(handler, "GUI Thread" + (i+1));
            thread.start();
        }
    }
}