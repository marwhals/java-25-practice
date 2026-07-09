package practice.main_module.concurrency.stable_values;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class PaymentGateway {

    public PaymentGateway() {
        System.out.println("Initializing payment object.");
    }

    public void process(Order order) {
        System.out.println("Payment initiated for orderId: " + order.getOrderId());

        try {
            Thread.sleep(Duration.of(1, TimeUnit.SECONDS.toChronoUnit()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Payment completed successfully.");
    }

}
