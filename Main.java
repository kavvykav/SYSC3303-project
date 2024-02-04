public class Main {
    public static void main(String[] args) {
        Thread scheduler, elevator, floor;
        int numFloors = 22;

        scheduler = new Thread(new Scheduler(), "Scheduler");
        elevator = new Thread(new Elevator(numFloors), "Elevator");
        floor = new Thread(new Floor("test_input.txt"), "Floor");

        scheduler.start();
        elevator.start();
        floor.start();
    }
}
