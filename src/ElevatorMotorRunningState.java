/**
 * ElevatorMotorRunningState
 */
public class ElevatorMotorRunningState implements ElevatorState {
    public FloorData doAction(Elevator elevator, FloorData receivedData) {
        System.out.println("Elevator Motor is running");
        try {
            // Simulate arrival
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.exit(130);
        }
        return null;
    }
}
