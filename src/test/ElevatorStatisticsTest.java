package test;

import common.ElevatorStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElevatorStatisticsTest {

    private ElevatorStatistics stats;

    @BeforeEach
    void setUp(){
        stats = new ElevatorStatistics();
    }

    /**
     * Test the getNumRequests method
     */
    @Test
    void testGetNumRequests() {
        assertEquals(0, stats.getNumRequests());
    }

    /**
     * Test the getNumServed method
     */
    @Test
    void testGetNumServed() {
        assertEquals(0, stats.getNumServed());
    }

    /**
     * Test the getNumFailed method
     */
    @Test
    void testGetNumFailed() {
        assertEquals(0, stats.getNumFailed());
    }

    /**
     * Test the getTimestamp method
     */
    @Test
    void testGetTimestamp() {
        assertEquals(0.0, stats.getTimestamp());
    }

    /**
     * Test the incrementNumRequests method
     */
    @Test
    void testIncrementNumRequests() {
        assertEquals(0, stats.getNumRequests());
        stats.incrementNumRequests();
        assertEquals(1, stats.getNumRequests());
        stats.incrementNumRequests();
        assertEquals(2, stats.getNumRequests());
        stats.incrementNumRequests();
        assertEquals(3, stats.getNumRequests());
    }

    /**
     * Test the incrementNumServed method
     */
    @Test
    void testIncrementNumServed() {
        assertEquals(0, stats.getNumServed());
        stats.incrementNumServed();
        assertEquals(1, stats.getNumServed());
        stats.incrementNumServed();
        assertEquals(2, stats.getNumServed());
        stats.incrementNumServed();
        assertEquals(3, stats.getNumServed());
    }

    /**
     * Test the incrementNumFailed method
     */
    @Test
    void testIncrementNumFailed() {
        assertEquals(0, stats.getNumFailed());
        stats.incrementNumFailed();
        assertEquals(1, stats.getNumFailed());
        stats.incrementNumFailed();
        assertEquals(2, stats.getNumFailed());
        stats.incrementNumFailed();
        assertEquals(3, stats.getNumFailed());
    }

    /**
     * Test the setTimeStamp method
     */
    @Test
    void testSetTimestamp() {
        assertEquals(0.0, stats.getTimestamp());
        stats.setTimestamp(1.0);
        assertEquals(1.0, stats.getTimestamp());
        stats.setTimestamp(10.0);
        assertEquals(10.0, stats.getTimestamp());
        stats.setTimestamp(100000.0);
        assertEquals(100000.0, stats.getTimestamp());
    }
}