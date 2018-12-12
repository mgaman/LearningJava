import java.util.concurrent.*;

class CSPExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // First we create a queue with capacity 1. This means that if there
        // is already an item in the queue waiting to be consumed, any other
        // threads wanting to add an item are blocked until it is consumed.
        // Also, if the consumer tries to get an element out of the queue, but
        // there aren't any items, the consumer will block.
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        // We create an executor service, which runs our number generators and
        // our summing process. They all get the same queue so they can
        // communicate with each other via messages.
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        // The NumberGenerators generate different numbers, and wait for different
        // periods of time.
        threadPool.submit(new NumberGenerator(5, 700, queue));
        threadPool.submit(new NumberGenerator(7, 650, queue));
        threadPool.submit(new NumberGenerator(11, 400, queue));

        // The SummingProcess returns the final sum, so we can get a Future<T>
        // that represents the answer at a future time and wait for it to finish.
        Future<Integer> totalSum = threadPool.submit(new SummingProcess(queue));

        // Waits for the SummingProcess to finish, after it's sum is > 100
        Integer sumResult = totalSum.get();

        System.out.println("Done! Sum was " + sumResult);

        // Interrupts the other threads for shutdown. You can also shutdown
        // threads more gracefully with shutdown() and awaitTermination(),
        // but here we just want them to exit immediately.
        threadPool.shutdownNow();
    }

    private static final class NumberGenerator implements Runnable {

        private final int theNumberToGenerate;
        private final int sleepPeriod;
        private final BlockingQueue<Integer> queue;

        public NumberGenerator(int theNumberToGenerate, int sleepPeriod, BlockingQueue<Integer> queue) {
            this.theNumberToGenerate = theNumberToGenerate;
            this.sleepPeriod = sleepPeriod;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                // Produce numbers indefinitely
                while (true) {
                    Thread.sleep(sleepPeriod);

                    // puts the integer into the queue, waiting as necessary for
                    // there to be space.
                    queue.put(theNumberToGenerate);
                }
            } catch (InterruptedException e) {
                // Allow our thread to be interrupted
                Thread.currentThread().interrupt();
            }
        }
    }

    private static final class SummingProcess implements Callable<Integer> {

        private final BlockingQueue<Integer> queue;

        public SummingProcess(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public Integer call() {
            try {
                int sum = 0;
                while (sum < 100) {
                    // take() gets the next item from the queue, waiting as necessary
                    // for there to be elements.
                    int nextInteger = queue.take();
                    sum += nextInteger;
                    System.out.println("Got " + nextInteger + ", total is " + sum);
                }
                return sum;
            } catch (InterruptedException e) {
                // Allow our thread to be interrupted
                Thread.currentThread().interrupt();
                return -1; // this will never run, but the compiler needs it
            }
        }
    }
}
