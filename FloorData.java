import java.time.LocalTime;

public class FloorData {

    // The time at which a passenger arrives
    private LocalTime timestamp;

    // The floor on which the passenger arrives
    private int floorNumber;

    // True: Up, False: Down
    private Boolean direction;

    // The floor the passenger wants to go to
    private int carButton;

    public FloorData(LocalTime timestamp, int floorNumber,
                     Boolean direction, int carButton) {
        this.timestamp = timestamp;
        this.floorNumber = floorNumber;
        this.direction = direction;
        this.carButton = carButton;
    }
}
