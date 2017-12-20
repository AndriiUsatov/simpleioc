import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringIoCRunner {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        System.out.println(Arrays.asList(context.getBeanDefinitionNames()));
        BeanDefinition definition = context.getBeanFactory().getBeanDefinition("pizzaRepo");
        System.out.println(definition);
    }
}
