import java.util.concurrent.CountDownLatch;

public class DeadlockDemo {

    static class Account {
        final String name;

        Account(String name) {
            this.name = name;
        }
    }

    static void transferUnsafe(
            Account first,
            Account second,
            CountDownLatch firstLocksAcquired) {

        synchronized (first) {
            System.out.println(
                    Thread.currentThread().getName()
                    + " locked " + first.name
            );

            // Wait until both threads hold their first lock
            firstLocksAcquired.countDown();

            try {
                firstLocksAcquired.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            System.out.println(
                    Thread.currentThread().getName()
                    + " waiting for " + second.name
            );

            synchronized (second) {
                System.out.println("Transfer completed.");
            }
        }
    }

    // Always acquire locks in the same order
    static void transferSafe(
            Account first,
            Account second,
            Account lock1,
            Account lock2) {

        synchronized (lock1) {
            synchronized (lock2) {
                System.out.println(
                        Thread.currentThread().getName()
                        + " safely transferred between "
                        + first.name + " and " + second.name
                );
            }
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        Account a = new Account("Account A");
        Account b = new Account("Account B");

        System.out.println("=== Demonstrating Deadlock ===");

        CountDownLatch latch = new CountDownLatch(2);

        Thread t1 = new Thread(
                () -> transferUnsafe(a, b, latch),
                "Thread-1"
        );

        Thread t2 = new Thread(
                () -> transferUnsafe(b, a, latch),
                "Thread-2"
        );

        // Daemon threads prevent the demonstration from
        // keeping the JVM alive after main() finishes.
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        t1.join(1000);
        t2.join(1000);

        if (t1.isAlive() && t2.isAlive()) {
            System.out.println(
                    "Deadlock detected: both threads are blocked."
            );
        } else {
            System.out.println(
                    "The deadlock was not confirmed in this run."
            );
        }

        System.out.println("\n=== Preventing Deadlock ===");

        Account safeA = new Account("Safe A");
        Account safeB = new Account("Safe B");

        // Both threads always lock safeA first, then safeB.
        Thread safeT1 = new Thread(
                () -> transferSafe(safeA, safeB, safeA, safeB),
                "Safe-Thread-1"
        );

        Thread safeT2 = new Thread(
                () -> transferSafe(safeB, safeA, safeA, safeB),
                "Safe-Thread-2"
        );

        safeT1.start();
        safeT2.start();

        safeT1.join();
        safeT2.join();

        System.out.println("Both safe transfers completed.");
    }
}