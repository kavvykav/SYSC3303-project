/**
 * Motor
 */
public class Motor {

    private Boolean isRunning = false;

    public Boolean isMotorRunning() {
        return isRunning;
    }

    public void start() {
        isRunning = true;
    }

    public void stop() {
        isRunning = false;
    }
}
