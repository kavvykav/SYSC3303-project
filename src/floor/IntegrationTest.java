package floor;

import common.Direction;
import common.ElevatorStatus;
import common.FloorRequest;
import common.NetworkConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scheduler.ElevatorClient;
import scheduler.Scheduler;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    private static Scheduler scheduler;
    private static Floor floor;

    @BeforeAll
    static void setUp() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        scheduler = new Scheduler();
        floor = new Floor("test_input.txt", address, NetworkConstants.SCHEDULER_PORT);
        Thread sender = new Thread(new Sender(floor), "Sender");
        Thread receiver = new Thread(new Receiver(floor), "Receiver");
        sender.start();
        receiver.start();
    }

    @Test
    void testFloorSchedulerIntegration() {
        // Add elevators to the scheduler
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 1));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 2));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 3));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 4));

        // Send requests from the floor to the scheduler
        //floor.sendRequests();

        // Wait for a short time to allow scheduler to process requests
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the scheduler has received the requests and processed them correctly
        // For example, you could check that the elevators are assigned to the correct requests
        assertEquals(1, scheduler.getClient(1).getStatus().getFloor());
        assertEquals(1, scheduler.getClient(2).getStatus().getFloor());
        assertEquals(1, scheduler.getClient(3).getStatus().getFloor());
        assertEquals(1, scheduler.getClient(4).getStatus().getFloor());
    }

    @Test
    void testSchedulerElevatorIntegration() {
        // Add elevators to the scheduler
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 1));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 2));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 3));
        scheduler.addClient(new ElevatorClient(InetAddress.getLoopbackAddress(), 5007, 4));

        // Simulate elevator arrival updates
        ElevatorStatus elevator1Status = new ElevatorStatus(1, 16, Direction.UP, true);
        ElevatorStatus elevator2Status = new ElevatorStatus(2, 20, Direction.DOWN, false);
        ElevatorStatus elevator3Status = new ElevatorStatus(3, 10, Direction.UP, false);
        ElevatorStatus elevator4Status = new ElevatorStatus(4, 1, Direction.DOWN, true);

        scheduler.getClient(1).setStatus(elevator1Status);
        scheduler.getClient(2).setStatus(elevator2Status);
        scheduler.getClient(3).setStatus(elevator3Status);
        scheduler.getClient(4).setStatus(elevator4Status);

        // Assert the scheduler elevator statuses are correct
        assertEquals(elevator1Status, scheduler.getClient(1).getStatus());
        assertEquals(elevator2Status, scheduler.getClient(2).getStatus());
        assertEquals(elevator3Status, scheduler.getClient(3).getStatus());
        assertEquals(elevator4Status, scheduler.getClient(4).getStatus());

        // Start the scheduler to process elevator arrival updates
        //floor.sendRequests();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //scheduler.serveRequests();

        // Simulate a floor request
        FloorRequest request = new FloorRequest(16, 0, true);
        ElevatorClient chosenElevator = scheduler.chooseElevator(request);
        assertEquals(chosenElevator, scheduler.chooseElevator(request));

        // Check if elevator is stuck, can not handle request
        elevator1Status.setDirection(Direction.STUCK);
        scheduler.getClient(1).setStatus(elevator1Status);
        assertFalse(scheduler.canServiceRequest(scheduler.getClient(1), new FloorRequest(16, 0, true)));
    }

    @AfterAll
    static void tearDown() {
        // Cleanup resources if needed
    }
}