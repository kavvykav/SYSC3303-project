
class MainTest {
    public static void main(String[] args) {
        Thread scheduler, elevator, floor;
        int numFloors = 22;

        floor = new Thread(new Floor("test_input.txt"), "Floor");
        scheduler = new Thread(new Scheduler(), "Scheduler");
        elevator = new Thread(new Elevator(numFloors), "Elevator");

        floor.start();
        scheduler.start();
        elevator.start();
    }
}