package common;

import java.io.Serializable;

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

    private final boolean goingUp;

    public FloorRequest(int floor, int elevator, boolean goingUp) {
        this.floor = floor;
        this.elevator = elevator;
        this.goingUp = goingUp;
    }

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
