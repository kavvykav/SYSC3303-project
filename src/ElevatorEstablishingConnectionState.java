/**
 * ElevatorEstablishingConnectionState
 */
public class ElevatorEstablishingConnectionState implements ElevatorState {
    public void doAction(Elevator elevator) {
        System.out.println("Elevator establishing connection with scheduler");
        elevator.setCurrentState(this);
    }
}
