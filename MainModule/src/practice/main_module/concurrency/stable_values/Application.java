package practice.main_module.concurrency.stable_values;

/**
 * Stable values notes:
 * - They replace double-checked locking with a safe, immutable and lazy initialization model.
 *
 * They are useful:
 * - When an object should be initialised only once
 * - Initalisation may be expensive
 * - Multiple threads may try to access it concurrently
 *
 * Traditional approaches
 * - Eager initialization - The object is created at application startup even if it is not needed immediately
 * - Lazy intialization - In this approach, the object is created on the first request after application startup
 * --- However it typically required synchronization which introduced performance overheads, potential bottlenecks and the risk of bugs due to unsafe or multiple initializations.
 *
 * Java 25 Introduces stable values to solve these problems which improves the application startup performance.
 * A stable value:
 * - Starts in an unset state at the time of initialization
 * - Can be set only once when accessed for the first time
 * - Requires no explicit synchornization, is lock-free and thread-safe
 * - Becaomes immutable after initialization
 *
 * Use cases of stable values
 * - Database client - Lazily initialise a database connection or client once and safely share it across all threads
 * - HTTP / REST Client: Create an expensive HTTP client (timeouts, TLS, connection pools) and resuse it application wide
 * - Payment Gateway / External SDKs - initializae third-party SDKs (Stripe, PayPal, Razorpay, etc.) only once and reuse the safely
 * - Configuration Loader - Load configuration from files, environment variables or remote config services a single time
 * - Thread-Safe Singleton Requirement - Replace complex synchoronized, volatile or double-checked locking singletons
 * - Caching Engines - Initialise in-memory caches (Caffeine, Redis client, Ehcache) and reuse them
 *
 * Stable values are ideal for heavy, shared, immtuable resource that must be initialized exactly once and resued safely across threads.
 *
 */

public class Application {

    static void main() {
        OrderService service = new OrderService();

        Thread thread1 = new Thread(() -> service.placeOrder(new Order("ORD12345")));
        Thread thread2 = new Thread(() -> service.placeOrder(new Order("ORD12346")));

        thread1.start();

        try {
            Thread.sleep(1200);
        } catch (Exception e) {
            // handle exception
        }

        System.out.println("-------------------------------------------------------------------------------------");
        thread2.start();

    }

}
