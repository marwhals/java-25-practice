package practice.main_module.concurrency;

import java.util.concurrent.StructuredTaskScope;

/**
 * Java 25 doesn't automatically propagate scoped values to child threads when they are created using unstructured concurrency APIs,
 * - such as:
 * - Thread.startVirtualThread(...) // Create a virtual thread directly
 * - Executors.newVirtualThreadPerTaskExecutor() // Create a thread using the executor service
 * - new Thread(...) // Create platform threads using new keyword
 *
 * These APIs create unstructed threads that DO NOT inherit the parent scopes value bindings.
 * -- This prevents accidental context leakage and ensures that the sceop values are only inherited when threads are created through structured concurrency mechanisms.
 *
 * Structured Task Scope
 * - When you create a structured task scope the JVM establishes a scope context that is bound to that scope
 * - All subtasks within the boundary automatically inherit any active scope values.
 *
 * Scoped Values provide a safe, clean, immutable way to pass context across threads.
 * --> They also work with virtual threads and structured concurrency
 * --> The moment the scope closes, the context is removed automatically, so no leftover data, no memory leaks.
 */

public class ScopedValuesVirtualThreads {
    record User(String id, String name) {};

    public final static ScopedValue<User> LOGGED_IN_USER = ScopedValue.newInstance();

    static void main() {

        User user = new User("id-111", "tim");

        ScopedValue.where(LOGGED_IN_USER, user).run(() -> {

            try(var scope = StructuredTaskScope.open()){
                scope.fork(() -> serviceA());
                scope.fork(() -> serviceB());

                scope.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private static void serviceA() {
        System.out.println("User data in service A: " + LOGGED_IN_USER.get());
        repository();
    }

    private static void serviceB() {
        System.out.println("User data in service B: " + LOGGED_IN_USER.get());
        repository();
    }

    private static void repository() {
        System.out.println("User data in repository: " + LOGGED_IN_USER.get());
    }


}
