package practice.main_module.concurrency;

import java.util.concurrent.StructuredTaskScope;

/**
 * Structured concurrency
 * <ul>
 * <li>Treats groups of related tasks running in different threads as single units of work</li>
 * </ul>
 * This ensures that:
 * <ul>
 * <li>The lifetime of subtasks is bounded by the lexical scope of the block (try-with-resources statement).</li>
 * <li>Error handling, result collection and cancellation are all coordinated in a clear way.</li>
 * </ul>
 * <br>
 * <p>
 * NOTE: this is a preview feature
 * </p>
 * <br>
 * When the scope closes, every remaining task, threads and resources will be cleaned up safely.
 *
 * This allows for Automatically Thread Management, Clean Result Composition, Simpler Error Handling and Clear Lifecycle
 */
public class ConcurrencyNewWay {

    static void main() {

        try (var scope = StructuredTaskScope.open()) {

            //Reuse fake API calls from classic Java concurrency example
            StructuredTaskScope.Subtask<Integer> task1 =
                    scope.fork(() -> ConcurrencyPreviousWay.getPriceFromSeller1());
            StructuredTaskScope.Subtask<Integer> task2 =
                    scope.fork(() -> ConcurrencyPreviousWay.getPriceFromSeller2());

            scope.join(); //Waits for all subtasks to complete or one of them to be canceled

            int price = Math.min(task1.get().intValue(), task2.get().intValue());

            System.out.println("Result: " + price);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
