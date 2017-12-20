import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pizza.repository.Order;
import pizza.repository.Pizza;
import pizza.repository.PizzaType;
import pizza.repository.User;
import pizza.service.OrderService;

public class Domain {
    public static void main(String[] args) {
        ConfigurableApplicationContext repoContext = new ClassPathXmlApplicationContext("repoContext.xml");
//        ConfigurableApplicationContext serviceContext = new ClassPathXmlApplicationContext("serviceContext.xml");
        ConfigurableApplicationContext serviceContext =
                new ClassPathXmlApplicationContext
                        (new String[]{"serviceContext.xml"}, repoContext);


        OrderService orderService = (OrderService) serviceContext.getBean("orderService");
        orderService.placeOrder(new User(2, "Jack", "Jackson"),
                new Pizza(1, "Hawaii", PizzaType.PIZZA_ONE, 100), new Pizza(2, "Cheese", PizzaType.PIZZA_TWO, 150));

        System.out.println(orderService.getAllOrders());

        System.out.println(serviceContext.getBeanFactory().getBeanDefinition("order"));
        System.out.println(serviceContext.getBean("order"));
        System.out.println(serviceContext.getBean(Order.class));


    }
}
