package ioc;

public class SimpleBeanDefinition implements BeanDefinition{

    private final String beanName;
    private final Class<?> beanClass;

    public SimpleBeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }
}
