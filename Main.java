public class Main {
    public static void main(String[] args) {
        Thread scheduler, elevator;
        int numFloors = 22;

        scheduler = new Thread(new Scheduler(), "Scheduler");
        elevator = new Thread(new Elevator(numFloors), "Elevator");

        scheduler.start();
        elevator.start();
    }
}
