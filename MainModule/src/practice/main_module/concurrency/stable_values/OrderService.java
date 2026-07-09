package practice.main_module.concurrency.stable_values;

public class OrderService {

    private StableValue<PaymentGateway> paymentGateway = StableValue.of(); // Holds instance that is not initialized

    void placeOrder(Order order) {
        // Set if it has not been intialized. Once initialized it is immutable and the same instance is used
        // JVM guarantees one initialization. No locks, no-reintialization, the value is immutable
        PaymentGateway payment = paymentGateway.orElseSet(() -> new PaymentGateway());
        payment.process(order);
    }

}
