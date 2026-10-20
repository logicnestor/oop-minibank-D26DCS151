import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        while (true) {

            System.out.print("Enter vehicle number (or done): ");
            String number = sc.next();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.next().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            int toll = 0;

            switch (vehicle.type()) {

                case "bike":
                    bikeCount++;
                    toll = 20;
                    break;

                case "car":
                    carCount++;
                    toll = 50;
                    break;

                case "truck":
                    truckCount++;
                    toll = 150;
                    break;

                default:
                    System.out.println("Invalid vehicle type.");
                    toll = 0;
            }

            totalToll = totalToll + toll;
        }

        System.out.println("\nTotal toll: " + totalToll);

        String mostFrequent;

        if (bikeCount >= carCount && bikeCount >= truckCount) {
            mostFrequent = "bike";
        } else if (carCount >= bikeCount && carCount >= truckCount) {
            mostFrequent = "car";
        } else {
            mostFrequent = "truck";
        }

        System.out.println("Most frequent: " + mostFrequent);

        sc.close();
    }
}