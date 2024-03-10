package test;

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
        status1 = new ElevatorStatus(1,12, ElevatorStatus.Direction.STATIONARY);
        status2 = new ElevatorStatus(2,7, ElevatorStatus.Direction.DOWN);
        status3 = new ElevatorStatus(3,2, ElevatorStatus.Direction.UP);
        status4 = new ElevatorStatus(4,9, ElevatorStatus.Direction.UP);
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
        assertEquals(ElevatorStatus.Direction.STATIONARY, status1.getDirection());
        assertEquals(ElevatorStatus.Direction.DOWN, status2.getDirection());
        assertEquals(ElevatorStatus.Direction.UP, status3.getDirection());
        assertEquals(ElevatorStatus.Direction.UP, status4.getDirection());
    }

    @Test
    void SetDirectionTest() {
        status1.setDirection(ElevatorStatus.Direction.UP);
        status2.setDirection(ElevatorStatus.Direction.STATIONARY);
        status3.setDirection(ElevatorStatus.Direction.STATIONARY);
        status4.setDirection(ElevatorStatus.Direction.DOWN);
        assertEquals(ElevatorStatus.Direction.UP, status1.getDirection());
        assertEquals(ElevatorStatus.Direction.STATIONARY, status2.getDirection());
        assertEquals(ElevatorStatus.Direction.STATIONARY, status3.getDirection());
        assertEquals(ElevatorStatus.Direction.DOWN, status4.getDirection());
    }
}