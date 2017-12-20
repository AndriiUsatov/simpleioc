package ioc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaConfig implements Config {
    private Map<String, Class<?>> beanDescription;
    public JavaConfig(Map<String, Class<?>> beanDescription) {
        this.beanDescription = beanDescription;
    }

    @Override
    public List<String> beanNames() {
        return new ArrayList<>(beanDescription.keySet());
    }

    @Override
    public BeanDefinition beanDefinition(String beanName) {
        Class<?> beanClass = beanDescription.get(beanName);
        return new SimpleBeanDefinition(beanName, beanClass);
    }
}
