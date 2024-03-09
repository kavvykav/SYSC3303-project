package common;

import java.io.Serializable;

public class ElevatorStatus implements Serializable {

    public enum Direction {
        STATIONARY,
        DOWN,
        UP
    }

    private int floor;

    private Direction direction;

    public ElevatorStatus() {
        floor = 1;
        direction = Direction.STATIONARY;
    }

    public synchronized int getFloor() {
        return floor;
    }

    public synchronized void setFloor(int floor) {
        this.floor = floor;
    }

    public synchronized Direction getDirection() {
        return direction;
    }

    public synchronized void setDirection(Direction direction) {
        this.direction = direction;
    }
}
