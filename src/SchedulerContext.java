/**
 * This is the context of the Scheduler state, which all it does is store the
 * current state, as well as provide a getter and a setter for the state.
 */

public class SchedulerContext {

    // The current state of the Scheduler
    private SchedulerState state;

    /**
     * The constructor for the SchedulerContext class.
     *
     * @return a SchedulerContext object
     */
    public SchedulerContext() {
        state = null;
    }

    /**
     * Sets the current scheduler state.
     *
     * @param state : the state we want to set the Scheduler
     */
    public void setSchedulerState(SchedulerState state) {
        this.state = state;
    }

    /**
     * Returns the current scheduler state.
     *
     * @return the current Scheduler state
     */
    public SchedulerState getSchedulerState() {
        return state;
    }
}
