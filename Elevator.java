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
        this.door = new Door();
        try {
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    public void run() {

    }

}
