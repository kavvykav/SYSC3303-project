package common;

import java.io.Serializable;

/**
 * This class represents a request for an Elevator made by the Floor system to the Scheduler.
 */
public class FloorRequest implements Serializable {

    // The floor we want the elevator to go to
    private final int floor;

    /*
    This field is used to specify which elevator to serve the request
    id > 0: Use this elevator
    id == 0: Use any elevator
    id < 0: Use any elevator except this one
     */
    private final int elevator;

    // The direction of the request
    // true: up, false: down
    private final boolean goingUp;

    /**
     * Create an instance of FloorRequest
     *
     * @param floor The floor number
     * @param elevator For specifying the elevator
     * @param goingUp The direction of the request
     */
    public FloorRequest(int floor, int elevator, boolean goingUp) {
        this.floor = Math.abs(floor);
        this.elevator = elevator;
        this.goingUp = goingUp;
    }

    /* Getter methods */
    public int getFloor() {
        return floor;
    }

    public int getElevator() {
        return elevator;
    }

    public boolean isGoingUp() {
        return goingUp;
    }
}
