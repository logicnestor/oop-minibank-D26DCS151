public class ParkingLot {

    // Current occupancy
    private int twoWheelers;
    private int fourWheelers;

    // Maximum capacity
    private final int twoCap;
    private final int fourCap;

    // Total revenue
    private static long revenue = 0;

    // Constructor
    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    // Park a vehicle
    public void park(String type) {

        if (type.equalsIgnoreCase("two")) {

            if (twoWheelers < twoCap) {
                twoWheelers++;
                revenue += 20;
                System.out.println("Two-wheeler parked successfully.");
            } else {
                System.out.println("Full");
            }

        } else if (type.equalsIgnoreCase("four")) {

            if (fourWheelers < fourCap) {
                fourWheelers++;
                revenue += 40;
                System.out.println("Four-wheeler parked successfully.");
            } else {
                System.out.println("Full");
            }

        } else {
            System.out.println("Invalid vehicle type.");
        }
    }

    // Vehicle leaves
    public void leave(String type) {

        if (type.equalsIgnoreCase("two")) {

            if (twoWheelers > 0) {
                twoWheelers--;
                System.out.println("Two-wheeler left.");
            } else {
                System.out.println("No two-wheeler to leave.");
            }

        } else if (type.equalsIgnoreCase("four")) {

            if (fourWheelers > 0) {
                fourWheelers--;
                System.out.println("Four-wheeler left.");
            } else {
                System.out.println("No four-wheeler to leave.");
            }

        } else {
            System.out.println("Invalid vehicle type.");
        }
    }

    // Display final status
    public void displayStatus() {
        System.out.println("\n=== Parking Lot Status ===");
        System.out.println("Two-wheelers : " + twoWheelers + "/" + twoCap);
        System.out.println("Four-wheelers: " + fourWheelers + "/" + fourCap);
        System.out.println("Revenue      : " + revenue);
    }

    public static void main(String[] args) {

        // Capacity: 2 two-wheelers and 2 four-wheelers
        ParkingLot lot = new ParkingLot(2, 2);

        System.out.println("=== Parking Events ===");

        // Two-wheelers
        lot.park("two");
        lot.park("two");

        // This should be refused because two-wheeler section is full
        lot.park("two");

        // Four-wheelers
        lot.park("four");
        lot.park("four");

        // This should be refused because four-wheeler section is full
        lot.park("four");

        // Leave events
        lot.leave("two");
        lot.leave("four");

        // Park again after space becomes available
        lot.park("two");
        lot.park("four");

        // Display final result
        lot.displayStatus();
    }
}