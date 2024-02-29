package scheduler;

import common.FloorData;

/**
 * This is the SchedulerState interface, which includes a doAction() method and
 * a toString() method.
 */
public interface SchedulerState {
    /* The action performed in each state */
    public FloorData doAction(Scheduler scheduler, FloorData floorData);

    /* Returns a string representation in each state */
    public String toString();
}
