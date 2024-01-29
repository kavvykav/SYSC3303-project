/**
 * ElevatorLamp
 */
public class ElevatorLamp {
    public Boolean isOn;

    public Boolean lampStatus() {
        return isOn;
    }

    public void toggleLamp() {
        isOn = !isOn;
    }

}
