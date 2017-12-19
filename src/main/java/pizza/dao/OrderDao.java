package pizza.dao;

import pizza.repository.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    Order save(Order order);
}
