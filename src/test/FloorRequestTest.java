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

    @Test
    void getFloorTest() {
        assertEquals(floor_request.getFloor(), 17);
    }

    @Test
    void getElevatorTest() {
        assertEquals(floor_request.getElevator(), 3);
    }

    @Test
    void isGoingUpTest() {
        assertFalse(floor_request.isGoingUp());
    }
}