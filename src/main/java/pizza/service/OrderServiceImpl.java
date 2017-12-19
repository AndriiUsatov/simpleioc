package pizza.service;

import pizza.dao.OrderDao;
import pizza.repository.Order;
import pizza.repository.Pizza;
import pizza.repository.User;

import java.util.Arrays;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public Order placeOrder(User user, Pizza... pizzas) {
        Order order = new Order(Arrays.asList(pizzas), user);
        return orderDao.save(order);
    }
}
