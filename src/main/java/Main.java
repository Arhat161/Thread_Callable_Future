import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        // Create one thread and override method 'call()' in interface 'Callable'
        // to get the result of the thread job
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // We use an object of type 'Future' to write to it the result of the work of the thread,
        // which is implemented as an implementation of the interface 'Callable'
        Future<Integer> future =
                executor.submit(() -> { // In this lambda expression, we don't need to parameterize our interface
                    // Java itself understands what type of value we return
                    System.out.println("Starting"); // starting message
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Finished"); // ending message

                    Random random = new Random(); // new random generator
                    int randomValue = random.nextInt(10); // new random digit
                    // we can throw exception and catch his in main thread
                    if (randomValue < 5) { // for example, if digit < 5
                        throw new Exception("Something bad happened"); // we throw new exception
                    }
                    return randomValue;
                });

        executor.shutdown(); // end all threads

        // now, in main thread we try to get result of thread job from object 'future'
        try {
            int result = future.get(); // method 'get' wait when thread finished
            System.out.println(result); // show result in console
        } catch (InterruptedException | ExecutionException e) { // in this section we catch our exception
            Throwable ex = e.getCause();
            System.out.println(ex.getMessage());
            // or use 'e.printStackTrace();'
        }
    }
}
