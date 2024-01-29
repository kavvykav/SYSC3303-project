/**
 * Door
 */
public class Door {
    private Boolean isOpen = false;

    public Boolean isDoorOpen() {
        return isOpen;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

}
