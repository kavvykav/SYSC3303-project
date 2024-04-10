package test;

import common.PassengerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerRequestTest {
    static PassengerRequest req;
    @BeforeEach
    void setUp(){
        req = new PassengerRequest(true, 2);
    }

    /**
     * Test isBoarding method
     */
    @Test
    void isBoardingTest() {
        assertTrue(req.isBoarding());
    }

    /**
     * Test getElevator method
     */
    @Test
    void getElevatorTest() {
        assertEquals(req.getElevator(), 2);
    }
}