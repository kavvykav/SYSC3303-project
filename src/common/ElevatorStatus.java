package common;

import java.io.Serializable;

public class ElevatorStatus implements Serializable {

    public enum Direction {
        STATIONARY,
        DOWN,
        UP
    }

    private int floor;

    private final int id;

    private Direction direction;

    public ElevatorStatus(int id) {
        this.id = id;
        floor = 1;
        direction = Direction.STATIONARY;
    }

    public int getId() {
        return id;
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
