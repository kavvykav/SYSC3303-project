package test;

import elevator.Elevator;
import elevator.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    static Elevator elevator;
    static InetAddress address;
    static int time;
    static Timer timer;
    @BeforeEach
    void setUp(){
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        elevator = new Elevator(20, address, 5007, 1, 15);
        time = 5;
        timer = new Timer(elevator, time);
    }

    @Test
    void getElevatorTest() { assertEquals(elevator, timer.getElevator()); }

    @Test
    void getTimeTest() {
        assertEquals(time, timer.getTime());
    }
}