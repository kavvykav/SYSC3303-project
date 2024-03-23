package elevator;

/**
 * The timer class represents a simple thread that sleeps for a specified amount of time and then sends a timeout
 * event to the elevator.
 */
public class Timer implements Runnable {

    // Motor instance to interrupt
    private final Elevator elevator;

    // The time (in seconds)
    private final int time;

    public Timer(Elevator elevator, int time) {
        this.elevator = elevator;
        this.time = time;
    }

    /**
     * Returns the elevator that the timer uses. For testing purposes only.
     *
     * @return the elevator object.
     */
    public Elevator getElevator(){return elevator;}

    /**
     * Returns the time that the timer uses. For testing purposes only.
     *
     * @return the time to use for timer.
     */
    public int getTime(){return time;}

    /**
     * Sleep for the specified amount of time and then generate a timeout event
     */
    public void run() {

        try {
            Thread.sleep(time * 1000L);
        } catch(InterruptedException e) {
            return;
        }
        elevator.timeout();
    }
}
