import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    Elevator elevator;
    Floor floor;
    Scheduler scheduler = new Scheduler();

    @BeforeEach
    void setUp(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007);
        floor = new Floor("src/test_input.txt", address, 5007);
    }
    @Test
    void sendData() {
    }

    @Test
    void recieveData() {
    }
}