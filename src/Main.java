import elevator.Elevator;
import floor.Floor;
import scheduler.Scheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    private static final int SERVER_PORT = 5000;
    private static final String INPUT_FILE = "test_input.txt";

    public static void main(String[] args) {

        Thread scheduler, elevator, floor;
        int numFloors = 22;
        InetAddress localHost;

        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return;
        }

        scheduler = new Thread(new Scheduler(), "scheduler.Scheduler");
        elevator = new Thread(new Elevator(numFloors, localHost, SERVER_PORT), "elevator.Elevator");
        floor = new Thread(new Floor(INPUT_FILE, localHost, SERVER_PORT), "floor.Floor");

        scheduler.start();
        elevator.start();
        floor.start();
    }
}
