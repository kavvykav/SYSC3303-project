/**
 * ElevatorLamp
 */
public class ElevatorLamp {
    public Boolean isOn;

    public Boolean isLampOn() {
        return isOn;
    }

    public void toggleLamp() {
        isOn = !isOn;
    }

}
