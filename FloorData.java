import java.io.Serializable;

public class FloorData implements Serializable {

    // For ensuring compatibility
    private static final long serialVersionUID = 1L;

    // The time at which a passenger arrives
    private final String timestamp;

    // The floor on which the passenger arrives
    private final int floorNumber;

    // True: Up, False: Down
    private final boolean direction;

    // The floor the passenger wants to go to
    private final int carButton;

    public FloorData(String timestamp, int floorNumber,
                     boolean direction, int carButton) {
        this.timestamp = timestamp;
        this.floorNumber = floorNumber;
        this.direction = direction;
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
