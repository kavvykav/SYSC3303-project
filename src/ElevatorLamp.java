/**
 * Lamp component of the elevator subsystem
 */
public class ElevatorLamp {
    public Boolean isOn = false;

    /**
     * Getter for isOn
     *
     * @return the current status of the lamp
     */
    public Boolean isLampOn() {
        return isOn;
    }

    /**
     * Turn lamp on
     */
    public void turnOn() {
        isOn = true;
    }

    /**
     * Turn lamp off
     */
    public void turnOff() {
        isOn = false;
    }

}
