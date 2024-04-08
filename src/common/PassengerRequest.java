package common;

import java.io.Serializable;

public class PassengerRequest implements Serializable {

    // true: Passenger is boarding elevator
    // false: Passenger is getting off elevator
    private final boolean boarding;

    // The ID of the elevator
    private final int elevator;

    /**
     * Create an PassengerRequests instance
     *
     * @param boarding Whether the passenger is boarding or getting off the elevator
     * @param elevator The ID of the elevator
     */
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
