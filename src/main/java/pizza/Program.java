package pizza;

import ioc.Config;
import ioc.JavaConfig;
import ioc.SimpleIoC;
import pizza.dao.OrderDaoImpl;
import pizza.dao.PizzaDaoImpl;
import pizza.repo.OrderRepo;
import pizza.repo.PizzaRepo;
import pizza.repository.Pizza;
import pizza.repository.PizzaType;
import pizza.repository.User;
import pizza.service.OrderService;
import pizza.service.OrderServiceImpl;
import pizza.service.PizzaService;
import pizza.service.PizzaServiceImpl;

import java.util.HashMap;

public class Program {
    public static void main(String[] args) {
        SimpleIoC simpleIoC = buildIoC();
        PizzaService pizzaService = (PizzaService) simpleIoC.getBean("pizzaService");
        OrderService orderService = (OrderService) simpleIoC.getBean("orderService");

        System.out.println(pizzaService.getAllPizza());

        System.out.println(pizzaService.getPizzaByName("Hawaii"));

        System.out.println(pizzaService.getPizzaByType(PizzaType.PIZZA_THREE));

        orderService.placeOrder(new User(2, "Jack", "Jackson"),
                new Pizza(1, "Hawaii", PizzaType.PIZZA_ONE, 100), new Pizza(2, "Cheese", PizzaType.PIZZA_TWO, 150));

        System.out.println(orderService.getAllOrders());
    }

    private static SimpleIoC buildIoC() {
        Config config = new JavaConfig(new HashMap<String, Class<?>>() {{
            put("orderRepo", OrderRepo.class);
            put("pizzaRepo", PizzaRepo.class);
            put("orderDao", OrderDaoImpl.class);
            put("pizzaDao", PizzaDaoImpl.class);
            put("orderService", OrderServiceImpl.class);
            put("pizzaService", PizzaServiceImpl.class);
        }});
        return new SimpleIoC(config);
    }
}
