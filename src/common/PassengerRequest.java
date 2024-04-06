package common;

import java.io.Serializable;

public class PassengerRequest implements Serializable {

    private final boolean boarding;

    private final int elevator;

    public PassengerRequest(boolean boarding, int elevator) {
        this.boarding = boarding;
        this.elevator = elevator;
    }

    public boolean isBoarding() {
        return boarding;
    }

    public int getElevator() {
        return elevator;
    }
}
