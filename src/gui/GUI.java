package gui;

import java.net.InetAddress;

import java.util.ArrayList;

import common.*;

import static common.NetworkConstants.GUI_PORT;
import static common.NetworkConstants.SCHEDULER_PORT;

public class GUI extends UDPClient {
    private static Console console;

    /**
     * The constructor for the GUIHandler class.
     *
     * @param address: the IP address that is being used.
     * @param port:    the port the handler is listening on.
     */
    public GUI(InetAddress address, int port, Console console) {
        super(address, SCHEDULER_PORT, port);
        this.console = console;
    }

    /**
     * The thread routine for the GUIHandler.
     */
    public void receiveUpdates() {
        ArrayList<Integer> elevatorsWithStuckDoors = new ArrayList<Integer>();
        while (true) {
            Object receivedData = receive();
            if (receivedData instanceof ElevatorStatus) {
                ElevatorStatus status = (ElevatorStatus) receivedData;
                int elevator = status.getId();
                if (status.getDirection() == Direction.STUCK) {
                    console.appendToErrorLog(elevator,
                                "Stuck between floors. Unavailable.");
                } else if (status.getDirection() == Direction.DOOR_STUCK) {
                    console.appendToErrorLog(elevator, "Door is stuck. Elevator will be temporarily out of service.");
                    elevatorsWithStuckDoors.add(Integer.valueOf(status.getId()));
                } else {
                    if (elevatorsWithStuckDoors.contains(Integer.valueOf(status.getId()))) {
                        console.appendToErrorLog(elevator,"Door is no longer stuck. Elevator is back in service.");
                        elevatorsWithStuckDoors.remove(Integer.valueOf(status.getId()));
                    } else {
                        Integer floor = null;
                        if (status.getFloor() < 1) {
                            floor = Integer.valueOf(1);
                        } else if (status.getFloor() > 22) {
                            floor = Integer.valueOf(22);
                        } else {
                            floor = Integer.valueOf(status.getFloor());
                        }
                        console.updateLocationField(elevator, floor);
                    }
                }
                console.updateDirectionField(elevator, status.getDirection());
            } else {
                System.err.println("Did not receive the correct type of information");
            }
        }
    }

    public static void main(String[] args) {
        InetAddress localhost = NetworkConstants.localHost();
        assert (localhost != null);

        Console console = new Console();
        GUI guiClient = new GUI(localhost, GUI_PORT, console);
        guiClient.receiveUpdates();
    }
}
