package floor;

import elevator.Elevator;
import gui.Console;
import gui.GUI;
import scheduler.Scheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import common.NetworkConstants;
import static common.NetworkConstants.GUI_PORT;
import static common.NetworkConstants.SCHEDULER_PORT;

public class AcceptanceTest implements Runnable{

    private int threadNumber;
    private static final String INPUT_FILE = "test_input.txt";

    private static Scheduler scheduler;
    private static GUI gui;
    private static Floor floor;

    public AcceptanceTest(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    /**
     * Thread routine to start the subsystems.
     */
    public void run() {
        if (threadNumber == 1) {
            scheduler = new Scheduler();
            scheduler.serveRequests();
        } else if (threadNumber == 2) {
            Console console = new Console();
            gui = new GUI(NetworkConstants.localHost(), GUI_PORT, console);
            gui.receiveUpdates();
        } else if (threadNumber == 3) {
            floor = new Floor(INPUT_FILE, NetworkConstants.localHost(), NetworkConstants.SCHEDULER_PORT);
            Thread sender = new Thread(new Sender(floor), "Sender");
            Thread receiver = new Thread(new Receiver(floor), "Receiver");
            sender.start();
            receiver.start();
        }
    }

    /**
     * Wrapper method for sleep. Sleeps for 500 ms.
     */
    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {

        int numFloors = 22;

        Thread schedulerThread = new Thread(new AcceptanceTest(1), "Scheduler");
        schedulerThread.start();
        sleep();

        Thread gui = new Thread(new AcceptanceTest(2), "GUI");
        gui.start();
        sleep();

        for (int i=1; i<4+1; i++) {
            Elevator elevator = new Elevator(numFloors, NetworkConstants.localHost(), SCHEDULER_PORT, i);
            Thread thread = new Thread(elevator, "Elevator " + i);
            thread.start();
        }
        sleep();

        Thread floor = new Thread(new AcceptanceTest(3), "Floor");
        floor.start();
    }
}