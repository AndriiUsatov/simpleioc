package pizza.service;

import pizza.repository.Order;
import pizza.repository.Pizza;
import pizza.repository.User;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order placeOrder(User user, Pizza ...pizzas);
}
