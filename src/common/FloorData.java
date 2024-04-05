package common;

import java.io.Serializable;

/**
 * The common.FloorData class represents the necessary information to be shared between the three main subsystems (floor.Floor,
 * scheduler.Scheduler, and elevator.Elevator). It represents a data structure to be sent over UDP.
 */
public class FloorData implements Serializable {

    // The time at which a passenger arrives
    private final String timestamp;

    // The floor on which the passenger arrives
    private final int floorNumber;

    // True: Up, False: Down
    private final boolean direction;

    // The floor the passenger wants to go to
    private final int carButton;

    // Flag indicating if the request has been fulfilled
    // True: request fulfilled, False: request pending
    private boolean status;

    /**
     * Basic constructor for common.FloorData class
     * NOTE: The status field is set to false by default
     *
     * @param timestamp The time the request was made
     * @param floorNumber The floor at which the request was made
     * @param direction The direction (Up or Down)
     * @param carButton The floor to which the passenger wants to go
     */
    public FloorData(String timestamp, int floorNumber,
            boolean direction, int carButton) {
        this.timestamp = timestamp;
        this.floorNumber = floorNumber;
        this.direction = direction;
        this.carButton = carButton;
        status = false;
    }

    /**
     * The equals method for the common.FloorData class.
     * NOTE: This method does not currently compare the status field
     *
     * @param o The FloorData object to compare it to
     *
     * @return true if common.FloorData objects are equivalent, false otherwise
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof FloorData)) {
            return false;
        }

        // typecast o to common.FloorData so that we can compare data members
        FloorData data = (FloorData) o;
        return timestamp.equals(data.timestamp)
                && floorNumber == data.floorNumber
                && direction == data.direction
                && carButton == data.carButton;
    }

    /**
     * Getter method for the status
     * @return The status of the request
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Setter method for the status
     * @param status The value we want to update the status to
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String returnTimeStamp(){return timestamp;}
    public int returnFloorNumber(){return floorNumber;}
    public boolean returnDirection(){return direction;}
    public int returnCarButton(){return carButton;}
}
