public class CounterRace {

    static final int THREADS = 8;
    static final int INCREMENTS = 500_000;

    static class Counter {
        int value = 0;

        // Unsafe: multiple threads can overwrite each other's updates
        void incrementUnsafe() {
            value++;
        }

        // Safe: only one thread can execute this method at a time
        synchronized void incrementSafe() {
            value++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Counter unsafeCounter = new Counter();
        Thread[] unsafeThreads = new Thread[THREADS];

        long start = System.nanoTime();

        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    unsafeCounter.incrementUnsafe();
                }
            });
            unsafeThreads[i].start();
        }

        for (Thread thread : unsafeThreads) {
            thread.join();
        }

        long unsafeTime = System.nanoTime() - start;

        Counter safeCounter = new Counter();
        Thread[] safeThreads = new Thread[THREADS];

        start = System.nanoTime();

        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    safeCounter.incrementSafe();
                }
            });
            safeThreads[i].start();
        }

        for (Thread thread : safeThreads) {
            thread.join();
        }

        long safeTime = System.nanoTime() - start;

        int expected = THREADS * INCREMENTS;

        System.out.println("Expected count: " + expected);
        System.out.println("Without synchronization: "
                + unsafeCounter.value);
        System.out.println("With synchronization: "
                + safeCounter.value);

        System.out.printf("Unsafe time: %.2f ms%n",
                unsafeTime / 1_000_000.0);
        System.out.printf("Safe time: %.2f ms%n",
                safeTime / 1_000_000.0);
    }
}