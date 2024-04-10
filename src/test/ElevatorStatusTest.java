package test;

import common.Direction;
import common.ElevatorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorStatusTest {
    static ElevatorStatus status1;
    static ElevatorStatus status2;
    static ElevatorStatus status3;
    static ElevatorStatus status4;

    @BeforeEach
    void setUp() {
        status1 = new ElevatorStatus(1,12, Direction.STATIONARY, true);
        status2 = new ElevatorStatus(2,7, Direction.DOWN, false);
        status3 = new ElevatorStatus(3,2, Direction.UP, true);
        status4 = new ElevatorStatus(4,9, Direction.UP, false);
    }

    /**
     * Test the getId method
     */
    @Test
    void GetIdTest() {
        assertEquals(1, status1.getId());
        assertEquals(2, status2.getId());
        assertEquals(3, status3.getId());
        assertEquals(4, status4.getId());
    }

    /**
     * Test the getFloor method
     */
    @Test
    void GetFloorTest() {
        assertEquals(12, status1.getFloor());
        assertEquals(7, status2.getFloor());
        assertEquals(2, status3.getFloor());
        assertEquals(9, status4.getFloor());
    }

    /**
     * Test the setFloor method
     */
    @Test
    void SetFloorTest() {
        status1.setFloor(17);
        status2.setFloor(1);
        status3.setFloor(22);
        status4.setFloor(56);
        assertEquals(17, status1.getFloor());
        assertEquals(1, status2.getFloor());
        assertEquals(22, status3.getFloor());
        assertEquals(56, status4.getFloor());
    }

    /**
     * Test the getDirection method
     */
    @Test
    void GetDirectionTest() {
        assertEquals(Direction.STATIONARY, status1.getDirection());
        assertEquals(Direction.DOWN, status2.getDirection());
        assertEquals(Direction.UP, status3.getDirection());
        assertEquals(Direction.UP, status4.getDirection());
    }

    /**
     * Test the setDirection method
     */
    @Test
    void SetDirectionTest() {
        status1.setDirection(Direction.UP);
        status2.setDirection(Direction.STATIONARY);
        status3.setDirection(Direction.STATIONARY);
        status4.setDirection(Direction.DOWN);
        assertEquals(Direction.UP, status1.getDirection());
        assertEquals(Direction.STATIONARY, status2.getDirection());
        assertEquals(Direction.STATIONARY, status3.getDirection());
        assertEquals(Direction.DOWN, status4.getDirection());
    }

    /**
     * Test the isStopped method
     */
    @Test
    void isStoppedTest(){
        assertFalse(status1.isStopped());
        assertFalse(status2.isStopped());
        assertFalse(status3.isStopped());
        assertFalse(status4.isStopped());
    }

    /**
     * Test the setStopped method
     */
    @Test
    void setStoppedTest(){
        status1.setStopped(true);
        assertTrue(status1.isStopped());
        status2.setStopped(true);
        assertTrue(status2.isStopped());
        status3.setStopped(true);
        assertTrue(status3.isStopped());
        status4.setStopped(true);
        assertTrue(status4.isStopped());
    }

    /**
     * Test the isGoingUp method
     */
    @Test
    void isGoingUpTest(){
        assertTrue(status1.isGoingUp());
        assertFalse(status2.isGoingUp());
        assertTrue(status3.isGoingUp());
        assertFalse(status4.isGoingUp());
    }

    /**
     * Test the setGoingUp method
     */
    @Test
    void setGoingUpTest(){
        status1.setGoingUp(false);
        assertFalse(status1.isGoingUp());
        status2.setGoingUp(true);
        assertTrue(status2.isGoingUp());
        status3.setGoingUp(false);
        assertFalse(status3.isGoingUp());
        status4.setGoingUp(true);
        assertTrue(status4.isGoingUp());
    }

    /**
     * Test the isEmpty method
     */
    @Test
    void isEmptyTest(){
        assertTrue(status1.isEmpty());
        assertTrue(status2.isEmpty());
        assertTrue(status3.isEmpty());
        assertTrue(status4.isEmpty());
    }

    /**
     * Test the setEmpty method
     */
    @Test
    void setEmptyTest(){
        status1.setEmpty(false);
        assertFalse(status1.isEmpty());
        status2.setEmpty(false);
        assertFalse(status2.isEmpty());
        status3.setEmpty(false);
        assertFalse(status3.isEmpty());
        status4.setEmpty(false);
        assertFalse(status4.isEmpty());
    }
}