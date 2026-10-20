public class ParallelArraySum {

    static final int SIZE = 2_000_000;
    static final int THREADS = 4;

    static class SharedTotal {
        long total = 0;

        void addUnsafe(long value) {
            total += value;
        }

        synchronized void addSafe(long value) {
            total += value;
        }
    }

    static long sumArray(int[] array) {
        long sum = 0;

        for (int value : array) {
            sum += value;
        }

        return sum;
    }

    static long runUnsafe(int[] array) throws InterruptedException {
        SharedTotal total = new SharedTotal();
        Thread[] workers = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            final int start = t * array.length / THREADS;
            final int end = (t + 1) * array.length / THREADS;

            workers[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    total.addUnsafe(array[i]);
                }
            });

            workers[t].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        return total.total;
    }

    static long runSynchronized(int[] array)
            throws InterruptedException {

        SharedTotal total = new SharedTotal();
        Thread[] workers = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            final int start = t * array.length / THREADS;
            final int end = (t + 1) * array.length / THREADS;

            workers[t] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    total.addSafe(array[i]);
                }
            });

            workers[t].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        return total.total;
    }

    static long runLocalSums(int[] array)
            throws InterruptedException {

        long[] partialSums = new long[THREADS];
        Thread[] workers = new Thread[THREADS];

        for (int t = 0; t < THREADS; t++) {
            final int workerIndex = t;
            final int start = t * array.length / THREADS;
            final int end = (t + 1) * array.length / THREADS;

            workers[t] = new Thread(() -> {
                long localSum = 0;

                for (int i = start; i < end; i++) {
                    localSum += array[i];
                }

                // Each thread writes to its own array element
                partialSums[workerIndex] = localSum;
            });

            workers[t].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        long total = 0;

        for (long partialSum : partialSums) {
            total += partialSum;
        }

        return total;
    }

    static long measure(String name, SumTask task)
            throws InterruptedException {

        long start = System.nanoTime();
        long result = task.run();
        long elapsed = System.nanoTime() - start;

        System.out.printf(
            "%-24s sum = %d, time = %.2f ms%n",
            name, result, elapsed / 1_000_000.0
        );

        return result;
    }

    @FunctionalInterface
    interface SumTask {
        long run() throws InterruptedException;
    }

    public static void main(String[] args) throws InterruptedException {

        int[] array = new int[SIZE];

        // Fill with deterministic values
        for (int i = 0; i < SIZE; i++) {
            array[i] = 1;
        }

        long expected = sumArray(array);

        System.out.println("Array size: " + SIZE);
        System.out.println("Threads: " + THREADS);
        System.out.println("Expected sum: " + expected);
        System.out.println();

        long unsafe = measure("Unsafe shared total",
                () -> runUnsafe(array));

        long synchronizedSum = measure("Synchronized total",
                () -> runSynchronized(array));

        long local = measure("Thread-local sums",
                () -> runLocalSums(array));

        System.out.println("\n=== Verification ===");
        System.out.println("Unsafe correct: " + (unsafe == expected));
        System.out.println("Synchronized correct: "
                + (synchronizedSum == expected));
        System.out.println("Thread-local correct: " + (local == expected));
    }
}