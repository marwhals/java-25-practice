package practice.main_module.concurrency.stable_values;


//TODO add lombok annotations
public class Order {

    private String orderId;

    public Order(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return java.util.Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order(orderId=" + orderId + ")";
    }
}