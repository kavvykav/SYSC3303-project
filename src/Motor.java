/**
 * Motor component of the elevator subsystem
 */
public class Motor {
    private Boolean isRunning = false;

    /**
     * Getter for isRunning
     *
     * @return the current status of the motor
     */
    public Boolean isMotorRunning() {
        return isRunning;
    }

    /**
     * Turn the motor on
     */
    public void start() {
        isRunning = true;
    }

    /**
     * Turn the motor off
     */
    public void stop() {
        isRunning = false;
    }
}
