package pizza.repository;

import java.util.List;

public class Order {
    private int orderId;
    private List<Pizza> pizzas;
    private User user;
    private double totalPrice;

    public Order(int orderId, List<Pizza> pizzas, User user) {
        this(pizzas, user);
        this.orderId = orderId;
    }

    public Order(List<Pizza> pizzas, User user) {
        this.pizzas = pizzas;
        this.user = user;
        for (Pizza pizza: pizzas) {
            totalPrice += pizza.getPrice();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", pizzas=" + pizzas +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
