package elevator;

import common.FloorRequest;
import common.PassengerRequest;

/**
 * ElevatorIdleState
 */
public class ElevatorIdleState implements ElevatorState {
    /**
     * When in the idle state, the Elevator simply waits for a request
     *
     * @param elevator The Elevator object
     * @param request Always null in this case
     *
     * @return The request received by the elevator
     */
    public FloorRequest doAction(Elevator elevator, FloorRequest request) {

        Object receivedData = elevator.receive();
        if (receivedData instanceof FloorRequest) {
            return (FloorRequest) receivedData;
        }
        else if (receivedData instanceof PassengerRequest) {
            PassengerRequest passengerRequest = (PassengerRequest) receivedData;
            if (passengerRequest.isBoarding()) {
                elevator.addPassenger();
            } else {
                elevator.removePassenger();
            }
        }
        return null;
    }
}
