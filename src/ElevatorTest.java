import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {

    @BeforeEach
    void setUp(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Elevator elevator = new Elevator(20, address, 5007);
    }
    @Test //inherited method
    void send() {
    }

    @Test //inherited method
    void receive() {
    }

    @Test //not used
    void goTo() {
    }

    @Test
    void add() {
    }

    @Test //not used
    void getCurrentFloor() {
    }

    @Test //not used
    void openDoor() {
    }

    @Test //not used
    void closeDoor() {
    }

    @Test
    void setCurrentState() {
    }

    @Test
    void getCurrentState() {
    }

    @Test
    void run() {
    }
}