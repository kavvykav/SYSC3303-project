package test;

import common.Direction;
import common.ElevatorStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scheduler.ElevatorClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorClientTest {
    static ElevatorClient client;
    @BeforeAll
    static void setUp(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        client = new ElevatorClient(address, 5007, 1);
    }
    @Test
    void GetAddressTest() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        assertEquals(address, client.getAddress());
    }

    @Test
    void GetPortTest() {
        assertEquals(5007, client.getPort());
    }

    @Test
    void GetStatusTest() {
        ElevatorStatus status = new ElevatorStatus(1, 1, Direction.STATIONARY, false);
        assertEquals(status, client.getStatus());
    }
}