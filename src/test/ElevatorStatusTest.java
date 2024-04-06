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
        status1 = new ElevatorStatus(1,12, Direction.STATIONARY);
        status2 = new ElevatorStatus(2,7, Direction.DOWN);
        status3 = new ElevatorStatus(3,2, Direction.UP);
        status4 = new ElevatorStatus(4,9, Direction.UP);
    }

    @Test
    void GetIdTest() {
        assertEquals(1, status1.getId());
        assertEquals(2, status2.getId());
        assertEquals(3, status3.getId());
        assertEquals(4, status4.getId());
    }

    @Test
    void GetFloorTest() {
        assertEquals(12, status1.getFloor());
        assertEquals(7, status2.getFloor());
        assertEquals(2, status3.getFloor());
        assertEquals(9, status4.getFloor());
    }

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

    @Test
    void GetDirectionTest() {
        assertEquals(Direction.STATIONARY, status1.getDirection());
        assertEquals(Direction.DOWN, status2.getDirection());
        assertEquals(Direction.UP, status3.getDirection());
        assertEquals(Direction.UP, status4.getDirection());
    }

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
}