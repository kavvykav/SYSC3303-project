/**
 * Button component of the elevator subsystem
 */
public class ElevatorButton {
    private Boolean isPressed = false;

    /**
     * Getter for isPressed
     *
     * @return the current status of the button
     */
    public Boolean isButtonPressed() {
        return isPressed;
    }

    /**
     * Press the button
     */
    public void press() {
        isPressed = true;
    }

    /**
     * Change status to unpressed when elevator arrives
     */
    public void arrive() {
        isPressed = false;
    }
}
