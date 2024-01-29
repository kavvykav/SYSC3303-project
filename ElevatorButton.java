/**
 * ElevatorButton
 */
public class ElevatorButton {
    private Integer floor;

    public ElevatorButton(Integer floor) {
        this.floor = floor;
    }

    public Integer press() {
        return floor;
    }

}
