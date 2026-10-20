import java.util.concurrent.CountDownLatch;

public class SeatBookingRace {

    static final int INITIAL_SEATS = 5;
    static final int CUSTOMERS = 10;

    static class BookingSystem {
        private int seatsLeft = INITIAL_SEATS;
        private int successfulBookings = 0;

        // Demonstration only: deliberately unsafe
        boolean bookUnsafe(CountDownLatch checked)
                throws InterruptedException {

            // Each thread reads the shared state without a lock
            int observedSeats = seatsLeft;

            // Ensure every customer has checked before updates begin
            checked.countDown();
            checked.await();

            if (observedSeats > 0) {
                seatsLeft--;
                successfulBookings++;
                return true;
            }

            return false;
        }

        // Thread-safe booking
        synchronized boolean book() {
            if (seatsLeft > 0) {
                seatsLeft--;
                successfulBookings++;
                return true;
            }

            return false;
        }

        int getSeatsLeft() {
            return seatsLeft;
        }

        int getSuccessfulBookings() {
            return successfulBookings;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        // Unsafe demonstration
        BookingSystem unsafe = new BookingSystem();
        CountDownLatch checked = new CountDownLatch(CUSTOMERS);
        Thread[] threads = new Thread[CUSTOMERS];

        for (int i = 0; i < CUSTOMERS; i++) {
            final int customer = i + 1;

            threads[i] = new Thread(() -> {
                try {
                    boolean booked = unsafe.bookUnsafe(checked);
                    System.out.println("Unsafe customer " + customer
                            + ": " + (booked ? "BOOKED" : "REJECTED"));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nUnsafe successful bookings: "
                + unsafe.getSuccessfulBookings());
        System.out.println("Unsafe seats left: "
                + unsafe.getSeatsLeft());

        // Safe demonstration
        BookingSystem safe = new BookingSystem();
        Thread[] safeThreads = new Thread[CUSTOMERS];

        for (int i = 0; i < CUSTOMERS; i++) {
            final int customer = i + 1;

            safeThreads[i] = new Thread(() -> {
                boolean booked = safe.book();
                System.out.println("Safe customer " + customer
                        + ": " + (booked ? "BOOKED" : "REJECTED"));
            });

            safeThreads[i].start();
        }

        for (Thread thread : safeThreads) {
            thread.join();
        }

        System.out.println("\nSafe successful bookings: "
                + safe.getSuccessfulBookings());
        System.out.println("Safe seats left: "
                + safe.getSeatsLeft());
    }
}