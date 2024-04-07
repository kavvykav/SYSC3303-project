package test;

import elevator.Elevator;
import elevator.Motor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class MotorTest {
    static Motor motor;
    static Elevator elevator;
    static InetAddress address;
    static int port = 5000;
    @BeforeAll
    static void setUp(){
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(22, address, port, 1);
        motor = new Motor(elevator, address, port);
    }
    @Test
    void getElevatorTest() {
        assertEquals(elevator, motor.getElevator());
    }
}