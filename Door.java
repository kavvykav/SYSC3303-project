/**
 * Door
 */
public class Door {
    private Boolean isOpen = false;

    public Boolean isDoorOpen() {
        return isOpen;
    }

    public void toggleDoor() {
        isOpen = !isOpen;
    }

}
