public class SchedulerIdleState implements SchedulerState {

    private Scheduler scheduler;

    public SchedulerIdleState(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void doAction(SchedulerContext context) {

    }
}
