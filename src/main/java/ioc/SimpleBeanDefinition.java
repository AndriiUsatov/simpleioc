package ioc;


public class SimpleBeanDefinition implements BeanDefinition {
    private String beanName;
    private Class<?> beanClass;

    public SimpleBeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClass;
    }
}
