import java.io.*;

public class FloorData implements Serializable {

    // The time at which a passenger arrives
    private String timestamp;

    // The floor on which the passenger arrives
    private int floorNumber;

    // True: Up, False: Down
    private Boolean direction;

    // The floor the passenger wants to go to
    private int carButton;

    public FloorData(String timestamp, int floorNumber,
                     Boolean direction, int carButton) {
        this.timestamp = timestamp;
        this.floorNumber = floorNumber;
        this.direction = direction;
        this.carButton = carButton;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Boolean getDirection() {
        return direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    public int getCarButton() {
        return carButton;
    }

    public void setCarButton(int carButton) {
        this.carButton = carButton;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof FloorData)) {
            return false;
        }

        // typecast o to FloorData so that we can compare data members
        FloorData data = (FloorData) o;

        // Compare the data members and return accordingly
        return timestamp.equals(data.timestamp)
                && floorNumber == data.floorNumber
                && direction == data.direction
                && carButton == data.carButton;
    }
}
