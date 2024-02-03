/**
 * The door component of the elevator subsystem
 */
public class Door {
    private Boolean isOpen = false;

    /**
     * Getter for isOpen
     *
     * @return the current status of the door
     */
    public Boolean isDoorOpen() {
        return isOpen;
    }

    /**
     * Open the door
     */
    public void open() {
        isOpen = true;
    }

    /**
     * Close the door
     */
    public void close() {
        isOpen = false;
    }

}
