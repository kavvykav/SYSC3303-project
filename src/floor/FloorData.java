package floor;

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

    // ID of the elevator currently carrying this passenger (0 if there is currently no elevator)
    private int elevator;

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
                     boolean direction, int carButton) throws IllegalArgumentException{

        this.timestamp = timestamp;
        this.floorNumber = Math.abs(floorNumber);
        this.direction = direction;
        this.carButton = Math.abs(carButton);

        if (direction && this.floorNumber > this.carButton) {
            throw new IllegalArgumentException("Request is to go up, yet destination is below current floor");
        }
        else if (!direction && this.floorNumber < this.carButton) {
            throw new IllegalArgumentException("Request is to go down, yet destination is above current floor");
        }
        elevator = 0;
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
                && carButton == data.carButton
                && elevator == data.elevator;
    }

    /* Getter and setter methods*/
    public int getFloorNumber(){
        return floorNumber;
    }

    public boolean getDirection(){
        return direction;
    }

    public int getCarButton(){
        return carButton;
    }

    public int getElevator() {
        return elevator;
    }

    public void setElevator(int elevator) {
        this.elevator = elevator;
    }
    public String getTimeStamp(){return timestamp;}
}
