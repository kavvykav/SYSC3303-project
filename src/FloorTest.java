import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class FloorTest {

    @BeforeEach
    void setUp(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Floor floor = new Floor("src/test_input.txt", address, 5007);
    }
    @Test //inherited method
    void send() {
    }

    @Test //inherited method
    void receive() {
    }

    @Test
    void run() {
    }
}