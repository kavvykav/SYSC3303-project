/**
 * ElevatorButton
 */
public class ElevatorButton {
    private Boolean isPressed = false;

    public Boolean isButtonPressed() {
        return isPressed;
    }

    public void press() {
        isPressed = true;
    }

    public void arrive() {
        isPressed = false;
    }
}
