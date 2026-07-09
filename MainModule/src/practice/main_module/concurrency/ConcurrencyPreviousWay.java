package practice.main_module.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Concurrency before Java 25
 * - Track all tasks individually
 * - Write logic for handling success or failures
 * - Executor requires manual shutdown
 * - Must remember to call get() to retrieve the results
 *
 * Problems if you forget:
 * - shutdown() - this would cause Threadpool leaks
 * - get() : This would cause loss of results or an error
 *
 * --> Thread management pollutes your business logic / the actual purpose of the code.
 *
 * Failure doesn't stop execution
 * Only first exception is thrown, other are missed --> debugging information is incomplete
 *
 */

public class ConcurrencyPreviousWay {

    static void main() {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Integer> seller1Result = executor.submit(() -> getPriceFromSeller1());
        System.out.println("Task 1 submitted");
        Future<Integer> seller2Result = executor.submit(() -> getPriceFromSeller2());
        System.out.println("Task 2 submitted");

        try {
            int price1 = seller1Result.get().intValue();
            int price2 = seller2Result.get().intValue();

            int minPrice = Math.min(price1, price2);

            System.out.println("Min price: " + minPrice);
        } catch (Exception e) {
            // Cancelling tasks
            seller1Result.cancel(true);
            seller2Result.cancel(true);

            System.err.println(e.getMessage());
        }
    }

    public static int getPriceFromSeller2() {
        // External API calls would go here.
        try {
            Thread.sleep(2000); // for thread interruption
            System.out.println("Task 2 running");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 550;
    }

    public static int getPriceFromSeller1() {
        // External API calls would go here.
        System.out.println("Task 1 running");
        //throw new RuntimeException("Error while calling Seller 1 API");
        return 560;
    }
}
