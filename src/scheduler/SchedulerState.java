package scheduler;

import common.FloorRequest;

/**
 * This is the SchedulerState interface, which includes a doAction() method and
 * a toString() method.
 */
public interface SchedulerState {
    /* The action performed in each state */
    FloorRequest doAction(Scheduler scheduler, FloorRequest request);

    /* Returns a string representation in each state */
    String toString();
}
