public class ProducerConsumer {

    static class SharedBuffer {

        private final int[] buffer = new int[3];
        private int count = 0;
        private int in = 0;
        private int out = 0;

        public synchronized void put(int item)
                throws InterruptedException {

            // Wait while the buffer is full
            while (count == buffer.length) {
                wait();
            }

            buffer[in] = item;
            in = (in + 1) % buffer.length;
            count++;

            System.out.println("Produced: " + item);

            // Notify waiting consumers or producers
            notifyAll();
        }

        public synchronized int take()
                throws InterruptedException {

            // Wait while the buffer is empty
            while (count == 0) {
                wait();
            }

            int item = buffer[out];
            out = (out + 1) % buffer.length;
            count--;

            System.out.println("Consumed: " + item);

            notifyAll();

            return item;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        SharedBuffer buffer = new SharedBuffer();
        final int totalItems = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.put(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.take();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("All items produced and consumed.");
    }
}