package common;

import java.io.Serializable;

/**
 * Represents the Status of an Elevator, used by the Scheduler to keep track of the positions of the elevators
 */
public class ElevatorStatus implements Serializable {

    /**
     * For representing the direction of the Elevator.
     * Moving up, moving down, or stationary
     */
    public enum Direction {
        STATIONARY,
        DOWN,
        UP
    }

    // The floor the elevator is currently on
    private int floor;

    // The ID of the elevator
    private final int id;

    // The direction of the elevator
    private Direction direction;

    /**
     * Initialize the status of an elevator
     *
     * @param id The id of the elevator
     * @param floor The current floor of the elevator
     * @param direction The direction of the elevator
     */
    public ElevatorStatus(int id, int floor, Direction direction) {
        this.id = id;
        this.floor = floor;
        this.direction = direction;
    }

    public synchronized int getId() {
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
