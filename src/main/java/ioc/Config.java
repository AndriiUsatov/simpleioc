package ioc;

import java.util.List;

public interface Config {

    List<String> beanNames();

    BeanDefinition getDefinition(String beanName);
}
