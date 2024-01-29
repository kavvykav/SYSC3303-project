/**
 * Door
 */
public class Door {
    private Boolean isOpen = false;

    public Boolean doorStatus() {
        return isOpen;
    }

    public void toggleDoor() {
        isOpen = !isOpen;
    }

}
