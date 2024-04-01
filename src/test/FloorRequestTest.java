package test;

import common.FloorRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorRequestTest {

    static FloorRequest testPacket;

    @BeforeAll
    static void setUp(){
        testPacket = new FloorRequest(1, "10:25:35",15,true,15, 15);
    }
    @Test
    void getTime() {
        assertEquals(testPacket.getTimeStamp(),"10:25:35");
    }

    @Test
    void getFloor() {
        assertEquals(testPacket.getFloorNumber(),15);
    }

    @Test
    void getDirection() {
        assertTrue(testPacket.getDirection());
    }

    @Test
    void getCarButton() {
        assertEquals(testPacket.getCarButton(),15);
    }
}