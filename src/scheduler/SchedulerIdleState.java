package scheduler;

import common.*;

import static common.NetworkConstants.FLOOR_PORT;
import static common.NetworkConstants.GUI_PORT;

/**
 * This state is the scheduler.Scheduler waiting to receive the request.
 */
public class SchedulerIdleState implements SchedulerState {

    /**
     * The action that is performed when the scheduler.Scheduler is in the Idle
     * state.
     */
    public FloorRequest doAction(Scheduler scheduler, FloorRequest request) {

        Object receivedObject = scheduler.receive();

        if (receivedObject instanceof FloorRequest) {
            return (FloorRequest) receivedObject;

        } else if (receivedObject instanceof PassengerRequest) {

            PassengerRequest request1 = (PassengerRequest) receivedObject;
            ElevatorClient client = scheduler.getClient(request1.getElevator());
            if (client != null) {
                scheduler.send(request1, client.getAddress(), client.getPort());
            }

        } else if (receivedObject instanceof ElevatorStatus) {
            // Received an update about an elevator
            ElevatorStatus status = (ElevatorStatus) receivedObject;
            ElevatorClient client = scheduler.getClient(status.getId());
            if (client != null) {
                client.setStatus(status);
                if (status.getDirection() == Direction.STUCK) {
                    scheduler.schedulerPrint("Elevator " + status.getId() + " is stuck between floors");
                    scheduler.send(status, NetworkConstants.localHost(), GUI_PORT);
                } else if (status.getDirection() == Direction.DOOR_STUCK) {
                    scheduler.schedulerPrint("Elevator " + status.getId() + " has a stuck door");
                    scheduler.send(status, NetworkConstants.localHost(), GUI_PORT );
                } else if (status.isStopped()) {
                    scheduler.send(status, NetworkConstants.localHost(), FLOOR_PORT);
                    scheduler.send(status, NetworkConstants.localHost(), GUI_PORT);
                } else {
                    scheduler.send(status, NetworkConstants.localHost(), GUI_PORT);
                }
            } else {
                ElevatorClient newClient = new ElevatorClient(scheduler.getReceivePacket().getAddress(),
                        scheduler.getReceivePacket().getPort(), status.getId());
                scheduler.addClient(newClient);
                scheduler.send(status, NetworkConstants.localHost(), GUI_PORT);
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
