package common;

import java.io.Serializable;

/**
 * Represents the Status of an Elevator, used by the Scheduler to keep track of the positions of the elevators
 */
public class ElevatorStatus implements Serializable {

    // The floor the elevator is currently on
    private int floor;

    // The ID of the elevator
    private final int id;

    // The direction of the elevator
    private Direction direction;

    private boolean stopped;

    private boolean goingUp;

    private boolean empty;

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
        this.stopped = false;
        this.goingUp = false;
        empty = true;
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

    public synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public synchronized boolean isGoingUp() {
        return goingUp;
    }

    public synchronized void setGoingUp(boolean goingUp) {
        this.goingUp = goingUp;
    }

    public synchronized boolean isEmpty() {
        return empty;
    }

    public synchronized void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof ElevatorStatus)) {
            return false;
        }

        // typecast o to common.FloorData so that we can compare data members
        ElevatorStatus data = (ElevatorStatus) o;
        return floor == data.floor
                && id == data.id
                && direction == data.direction
                && stopped == data.stopped
                && goingUp == data.goingUp;
    }
}
