package ioc;

public interface BeanDefinition {
    String getBeanName();
    Class<?> getBeanClass();
}
