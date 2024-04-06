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

    @Test
    void isBoarding() {
        assertTrue(req.isBoarding());
    }

    @Test
    void getElevator() {
        assertEquals(req.getElevator(), 2);
    }
}