package scheduler;

import common.ElevatorStatus;
import common.FloorRequest;

/**
 * This state is the scheduler.Scheduler waiting to receive the request.
 */
public class SchedulerIdleState implements SchedulerState {

    /**
     * The action that is performed when the scheduler.Scheduler is in the Idle
     * state.
     */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest data) {

        Object receivedObject = scheduler.receive();

        if (receivedObject instanceof FloorRequest) {
            // Received a request
            return (FloorRequest) receivedObject;
        } else if (receivedObject instanceof ElevatorStatus) {
            // Received an update about an elevator
            ElevatorStatus status = (ElevatorStatus) receivedObject;
            ElevatorClient client = scheduler.getClient(status.getId());
            if (client != null) {
                client.setStatus(status);
                if (status.getDirection() == ElevatorStatus.Direction.STUCK) {
                    scheduler.schedulerPrint("Elevator " + status.getId() + " is stuck between floors");
                }
            } else {
                ElevatorClient newClient = new ElevatorClient(scheduler.getReceivePacket().getAddress(),
                        scheduler.getReceivePacket().getPort(), status);
                scheduler.addClient(newClient);
            }
        }
        return null;
    }

    /**
     * Returns the string representation of the state.
     *
     * @return the string representation of the state
     */
    public String toString() {
        return "Scheduler Idle State";
    }
}
