package test;

import common.ElevatorStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scheduler.ElevatorClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorClientTest {
    static ElevatorClient client;

    static ElevatorStatus status;

    @BeforeAll
    static void setUp(){
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        status = new ElevatorStatus(1, 1, ElevatorStatus.Direction.STATIONARY, 15);
        client = new ElevatorClient(address, 5007, status);
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
        assertEquals(status, client.getStatus());
    }
}