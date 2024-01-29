import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.io.IOException;

public class Scheduler implements Runnable {

    private DatagramSocket sendSocket, recieveSocket;
    private byte[] message = new byte[256];
    private boolean running;

    /**
     * This is the constructor for the scheduler.
     **/
    public Scheduler() {
        try {
            sendSocket = new DatagramSocket();
            recieveSocket = new DatagramSocket(5000);
        }
        catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
        
    }
     
    /**
     * This is the thread routine for the scheduler thread.
     **/
    public void run() {
        running = true;
        while(running) {
            DatagramPacket recievePacket = new DatagramPacket(message, message.length);
            try {
               System.out.println("Waiting for packet from the elevator...");
               recieveSocket.recieve(recievePacket);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            message = recievePacket.getData();
            DatagramPacket sendPacket = new DatagramPacket(message, recievePacket.getLength(),
            recievePacket.getAddress(), recievePacket.getPort());

            try {
               sendSocket.send(sendPacket);
            }catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        sendSocket.close();
        recieveSocket.close();
    }
}
