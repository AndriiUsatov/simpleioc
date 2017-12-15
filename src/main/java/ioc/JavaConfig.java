package ioc;

import java.util.*;

public class JavaConfig implements Config {

    private final Map<String, Class<?>> beanDescription;

    public JavaConfig(Map<String, Class<?>> beanDescription) {
        this.beanDescription = beanDescription;
    }

    @Override
    public List<String> beanNames() {
        return new ArrayList<>(beanDescription.keySet());
    }

    @Override
    public BeanDefinition getDefinition(String beanName) {
        Class<?> beanClass = beanDescription.get(beanName);
        return new SimpleBeanDefinition(beanName, beanClass);
    }
}
