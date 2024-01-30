/**
 * ElevatorLamp
 */
public class ElevatorLamp {
    public Boolean isOn = false;

    public Boolean isLampOn() {
        return isOn;
    }

    public void turnOn() {
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

}
