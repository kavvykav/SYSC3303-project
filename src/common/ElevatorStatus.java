package common;

import java.io.Serializable;

/**
 * Represents the Status of an Elevator, used by the Elevator Scheduler systems to keep track of the position and
 * direction of the elevators.
 */
public class ElevatorStatus implements Serializable {

    // The floor the elevator is currently on
    private int floor;

    // The ID of the elevator
    private final int id;

    // The direction of the elevator
    private Direction direction;

    // Flag indicating if the elevator has stopped at a floor (to drop off or pick up a passenger)
    private boolean stopped;

    // The direction in which the elevator is serving passengers
    // true: up, false: down
    private boolean goingUp;

    // For capacity limits
    private boolean empty;

    /**
     * Initialize the status of an elevator
     *
     * @param id The id of the elevator
     * @param floor The current floor of the elevator
     * @param direction The direction of the elevator
     */
    public ElevatorStatus(int id, int floor, Direction direction, boolean goingUp) {
        this.id = id;
        this.floor = floor;
        this.direction = direction;
        this.stopped = false;
        this.goingUp = goingUp;
        empty = true;
    }

    /* Getter and setter methods */
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

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public boolean isGoingUp() {
        return goingUp;
    }

    public void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    /**
     * Equals method for ElevatorStatus
     *
     * @param o the status object we are comparing
     * @return true if the statuses are equivalent, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof ElevatorStatus)) {
            return false;
        }

        // typecast o to ElevatorStatus so that we can compare data members
        ElevatorStatus data = (ElevatorStatus) o;
        return floor == data.floor
                && id == data.id
                && direction == data.direction
                && stopped == data.stopped
                && goingUp == data.goingUp
                && empty == data.empty;
    }
}
