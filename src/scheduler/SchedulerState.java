package scheduler;

import common.FloorRequest;

/**
 * This is the SchedulerState interface, which includes a doAction() method and
 * a toString() method.
 */
public interface SchedulerState {
    /* The action performed in each state */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest floorData);

    /* Returns a string representation in each state */
    public String toString();
}
