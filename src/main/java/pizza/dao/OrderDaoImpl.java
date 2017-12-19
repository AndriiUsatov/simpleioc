package pizza.dao;

import pizza.repo.OrderRepo;
import pizza.repository.Order;

import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private OrderRepo orderRepo;

    public OrderDaoImpl(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.getOrders();
    }

    @Override
    public Order save(Order order) {
        int lastId = orderRepo.getLastId();
        order.setOrderId(lastId + 1);
        orderRepo.addOrder(order);
        return order;
    }
}
