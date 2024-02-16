/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {
    public void doAction(Elevator elevator) {
        System.out.println("Elevator establishing connection with scheduler");
        elevator.setCurrentState(this);
        if (elevator.send("elevator") != 0) {
            System.err.println("Elevator: Failed to send initial message");
            System.exit(1);
        }
    }
}
