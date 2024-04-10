package floor;

import elevator.Elevator;
import gui.Console;
import gui.GUI;
import scheduler.Scheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static common.NetworkConstants.GUI_PORT;
import static common.NetworkConstants.SCHEDULER_PORT;

public class AcceptanceTest {
    private static final int SERVER_PORT = 5000;
    private static final String INPUT_FILE = "test_input.txt";

    public static void main(String[] args) {

        int numFloors = 22;
        InetAddress localHost;

        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return;
        }

        Console console = new Console();
        Floor floor = new Floor(INPUT_FILE, localHost, SERVER_PORT);
        Scheduler scheduler = new Scheduler();
        GUI guiClient = new GUI(localHost, GUI_PORT, console);

        scheduler.serveRequests();

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        guiClient.receiveUpdates();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        for (int i=1; i<4+1; i++) {
            Elevator elevator = new Elevator(numFloors, localHost, SCHEDULER_PORT, i);
            Thread thread = new Thread(elevator, "Elevator " + i);
            thread.start();
        }
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        floor.sendRequests();
        floor.receiveResponses();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Efficiency of the system: " + scheduler.getStats().getNumServed() / scheduler.getStats().getNumRequests());
    }
}