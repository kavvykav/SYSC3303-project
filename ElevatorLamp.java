/**
 * ElevatorLamp
 */
public class ElevatorLamp {
    public Boolean isOn = false;

    public Boolean isLampOn() {
        return isOn;
    }

    public void toggleLamp() {
        isOn = !isOn;
    }

}
