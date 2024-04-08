package test;

import floor.FloorData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorDataTest {

    static FloorData testPacket;

    @BeforeAll
    static void setUp() {
        testPacket = new FloorData("10:25:35", 15, true, 15);
    }

    @Test
    void getTimeTest() {
        assertEquals(testPacket.getTimeStamp(), "10:25:35");
    }

    @Test
    void getFloorTest() {
        assertEquals(testPacket.getFloorNumber(), 15);
    }

    @Test
    void getDirectionTest() {
        assertTrue(testPacket.getDirection());
    }

    @Test
    void getCarButtonTest() {
        assertEquals(testPacket.getCarButton(), 15);
    }

    @Test
    void getElevatorTest() {
        assertEquals(testPacket.getElevator(), 0);
    }

    @Test
    void setElevatorTest() {
        testPacket.setElevator(5);
        assertEquals(5, testPacket.getElevator());
    }

}
