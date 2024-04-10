package test;

import common.FloorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorRequestTest {
    static FloorRequest floor_request;
    @BeforeEach
    void setUp(){
        floor_request = new FloorRequest(17, 3, false);
    }

    /**
     * Test getFloor method
     */
    @Test
    void getFloorTest() {
        assertEquals(floor_request.getFloor(), 17);
        floor_request = new FloorRequest(-8, 0, false);
        assertEquals(floor_request.getFloor(), 8);
    }

    /**
     * Test getElevator method
     */
    @Test
    void getElevatorTest() {
        assertEquals(floor_request.getElevator(), 3);
    }

    /**
     * Test isGoingUp method
     */
    @Test
    void isGoingUpTest() {
        assertFalse(floor_request.isGoingUp());
    }
}