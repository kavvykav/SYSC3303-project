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

    /**
     * Test getTime method
     */
    @Test
    void getTimeTest() {
        assertEquals(testPacket.getTimeStamp(), "10:25:35");
    }

    /**
     * Test getFloor method
     */
    @Test
    void getFloorTest() {
        assertEquals(testPacket.getFloorNumber(), 15);
    }

    /**
     * Test getDirection method
     */
    @Test
    void getDirectionTest() {
        assertTrue(testPacket.getDirection());
    }

    /**
     * Test getCarButton method
     */
    @Test
    void getCarButtonTest() {
        assertEquals(testPacket.getCarButton(), 15);
    }

    /**
     * Test getElevator method
     */
    @Test
    void getElevatorTest() {
        assertEquals(testPacket.getElevator(), 0);
    }

    /**
     * Test setElevator method
     */
    @Test
    void setElevatorTest() {
        testPacket.setElevator(5);
        assertEquals(5, testPacket.getElevator());
    }

}
