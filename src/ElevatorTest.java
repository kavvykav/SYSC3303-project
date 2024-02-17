import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    Elevator elevator;
    Scheduler scheduler;
    Floor floor;
    @BeforeEach
    void setUp() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007);
    }

    @Test
    void testNumButtons () {
    }
    @Test
    void testNumLamps () {
    }
    @Test
    void doorClosed () {
    }
    @Test
    void doorOpen () {
    }
}