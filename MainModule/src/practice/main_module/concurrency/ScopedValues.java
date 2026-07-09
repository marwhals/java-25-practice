package practice.main_module.concurrency;

/**
 * Scoped Values
 * - Lightweight, Immutable and Thread-Safe
 *
 * Traditional Approach
 * - Pass the value manually through every method
 * - Use ThreadLocal
 *
 * Memory Leak Risk
 * - ThreadLocal values are stored inside long-lived threads, so in thread pools they can remain in memory unless explicitly removed
 * -- even after the ThreadLocal itself is garbage-collected
 *
 * State Leakage
 * - Because thread pools are resued, leftover thread-bound data from a previous task can be seen by the next task, causing state leakage.
 *
 * Not Immutable
 * - ThreadLocal values are changeable from anywhere in the call stack, which means the data can change unexpectedly and the program is harder to debug.
 *
 * Dangerous with Virtual Threads
 * - Virtual threads can run on different carrier threads. This causes Data Leakage between tasks and unpredictable behaviour.
 *
 * Breaks structured concurrency principles
 * - Subtasks start within a scope, Subtasks end within the scope an all state must be cleaned when the scope ends.
 * - But it ThreadLocal, data remains attached to threads which survive after the scope ends.
 *
 * Introducing Scoped Values
 * - Safer and more efficient alternative to ThreadLocal for sharing immutable data across threads.
 * - Scoped values are lightweight, avoid memory leaks, hidden state and synchronization overhead.
 * - Scoped values are useful for building more robust concurrent applications.
 */

public class ScopedValues {

    record User(String id, String name) {};

    public final static ScopedValue<User> LOGGED_IN_USER = ScopedValue.newInstance();

    static void main() {
        User user = new User("id-101", "Bob");

        ScopedValue.where(LOGGED_IN_USER, user).run(() -> service());
    }

    private static void service() {
        System.out.println("User data in service: " + LOGGED_IN_USER.get());
        repository();
    }

    private static void repository() {
        System.out.println("User data in repository: " + LOGGED_IN_USER.get());
    }

}
