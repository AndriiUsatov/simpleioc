package pizza.repo;

import pizza.repository.Order;
import pizza.repository.Pizza;
import pizza.repository.PizzaType;
import pizza.repository.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepo {
    private List<Order> orders = new ArrayList<Order>(){{
        add(new Order(1,
                Arrays.asList(new Pizza(1, "Hawaii", PizzaType.PIZZA_ONE, 100),
                        new Pizza(4, "Bacon", PizzaType.PIZZA_THREE, 130)),
                new User(1, "John", "Doe")));
    }};

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public int getLastId() {
        return (orders.size() == 0) ? 0 : orders.get(orders.size()-1).getOrderId();
    }
}
