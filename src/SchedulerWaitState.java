public class SchedulerWaitState implements SchedulerState {

    private Scheduler scheduler;

    public SchedulerWaitState(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void doAction(SchedulerContext context) {

    }
}
