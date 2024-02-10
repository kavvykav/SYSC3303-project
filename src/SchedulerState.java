/**
 * This is the state interface for the Scheduler, used to implement the
 * state design pattern.
 */
public interface SchedulerState {

    /**
     * The method used to do perform the action for that particular state.
     *
     * @param context : the context (or the object that carries the current state)
     */
    public void doAction(SchedulerContext context);
}
