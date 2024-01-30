import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Elevator
 */
public class Elevator implements Runnable {

    private ArrayList<ElevatorButton> buttons;
    private ArrayList<ElevatorLamp> lamps;
    private Motor motor;
    private Door door;
    private Integer currentFloor;
    DatagramSocket sendReceiveSocket;
    DatagramPacket sendPacket, receivePacket;

    public Elevator(int numFloors) {
        this.motor = new Motor();
        this.lamps = new ArrayList<ElevatorLamp>(numFloors);
        this.buttons = new ArrayList<ElevatorButton>(numFloors);
        for (int i = 0; i < 20; i++) {
            this.lamps.add(new ElevatorLamp());
            this.buttons.add(new ElevatorButton());
        }
        this.door = new Door();
        try {
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    public void goTo(int floor) {
        currentFloor = floor;
        buttons.get(floor - 1).arrive();
        lamps.get(floor - 1).turnOff();
    }

    public void add(int floor) {
        buttons.get(floor - 1).press();
        lamps.get(floor - 1).turnOn();
    }

    public void run() {

    }

}
