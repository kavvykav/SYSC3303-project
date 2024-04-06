package scheduler;

import common.Direction;
import common.ElevatorStatus;
import common.FloorData;

/**
 * This state is the scheduler.Scheduler waiting to receive the request.
 */
public class SchedulerIdleState implements SchedulerState {

    /**
     * The action that is performed when the scheduler.Scheduler is in the Idle
     * state.
     */
    public FloorData doAction(Scheduler scheduler, FloorData data) {

        Object receivedObject = scheduler.receive();

        if (receivedObject instanceof FloorData) {
            // Received a request
            return (FloorData) receivedObject;
        } else if (receivedObject instanceof ElevatorStatus) {
            // Received an update about an elevator
            ElevatorStatus status = (ElevatorStatus) receivedObject;
            ElevatorClient client = scheduler.getClient(status.getId());
            if (client != null) {
                client.setStatus(status);
                if (status.getDirection() == Direction.STUCK) {
                    scheduler.schedulerPrint("Elevator " + status.getId() + " is stuck between floors");
                }
            } else {
                ElevatorClient newClient = new ElevatorClient(scheduler.getReceivePacket().getAddress(),
                        scheduler.getReceivePacket().getPort(), status.getId());
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
