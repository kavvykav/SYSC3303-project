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
