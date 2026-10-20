public class CinemaShow {

    // Instance variables
    private String title;
    private int seatsAvailable;
    private final int capacity;

    // Static variable
    private static int totalBooked = 0;

    // Constructor with title and capacity
    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    // Constructor with only title
    public CinemaShow(String title) {
        this(title, 100);
    }

    // Book seats
    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        } else {
            return false;
        }
    }

    // Cancel seats
    public void cancel(int n) {
        seatsAvailable += n;

        if (seatsAvailable > capacity) {
            seatsAvailable = capacity;
        }
    }

    // Getter for available seats
    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    // Static getter for total booked seats
    public static int getTotalBooked() {
        return totalBooked;
    }

    // Main method
    public static void main(String[] args) {

        CinemaShow show = new CinemaShow("Avengers", 50);

        System.out.println("Movie: Avengers");
        System.out.println("Initial Seats: " + show.getSeatsAvailable());

        System.out.println("\nBook 20 seats:");
        System.out.println("Booking Successful: " + show.book(20));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nBook 15 seats:");
        System.out.println("Booking Successful: " + show.book(15));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nBook 20 seats:");
        System.out.println("Booking Successful: " + show.book(20));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nCancel 10 seats:");
        show.cancel(10);
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nBook 12 seats:");
        System.out.println("Booking Successful: " + show.book(12));
        System.out.println("Seats Available: " + show.getSeatsAvailable());

        System.out.println("\nTotal Successfully Booked Seats: " + CinemaShow.getTotalBooked());
    }
}