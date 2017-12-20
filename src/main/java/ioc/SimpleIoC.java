package ioc;

import org.apache.commons.text.WordUtils;
import proxy.BenchmarkProxyHandler;

import java.lang.reflect.*;
import java.util.*;

public class SimpleIoC {
    private Config config;
    private Map<String, Object> container;

    public SimpleIoC(Config config) {
        container = new HashMap<>();
        this.config = config;
        checkUniqueBeanNames();
    }


    private void checkUniqueBeanNames() {
        Set<String> beanNames = new HashSet<>(config.beanNames());
        if (beanNames.size() < config.beanNames().size()) {
            throw new IllegalArgumentException();
        }
    }


    public List<String> beanDefinitions() {
        return config.beanNames();
    }


    public Object getBean(String beanName) {
        if (container.containsKey(beanName)) {
            return container.get(beanName);
        }

        try {
            BeanDefinition beanDefinition = config.beanDefinition(beanName);
            Object bean = buildBeanFromDefinition(beanDefinition);
            container.put(beanName, bean);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        return container.get(beanName);
    }


    private Object buildBeanFromDefinition(BeanDefinition beanDefinition) throws Exception  {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor constructor = beanClass.getDeclaredConstructors()[0];

        Object bean;
        if (isDefaultConstructor(constructor)) {
            bean = instantiateBeanWithDefaultConstructor(beanClass);
        } else {
            bean = instantiateBeanWithConstructor(beanClass);
        }

        callInitMethod(bean);

        bean = createBenchmarkProxy(bean);

        return bean;
    }


    private boolean isDefaultConstructor(Constructor constructor) {
        int constructorParametersCount = constructor.getParameterCount();
        return constructorParametersCount == 0;
    }


    private String getBeanNameFromParameterClassName(Parameter parameter) {
        return WordUtils.uncapitalize(parameter.getType().getSimpleName());
    }


    private Object instantiateBeanWithConstructor(Class clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = clazz.getDeclaredConstructors()[0];
        int constructorParametersCount = constructor.getParameterCount();
        Object[] arguments = new Object[constructorParametersCount];

        for (int index = 0; index < constructorParametersCount; index++) {
            Parameter parameter = constructor.getParameters()[index];
            String injectedBeanName =getBeanNameFromParameterClassName(parameter);
            arguments[index] = getBean(injectedBeanName);
        }

        return constructor.newInstance(arguments);
    }


    private Object instantiateBeanWithDefaultConstructor(Class clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }


    private void callInitMethod(Object bean) throws Exception{
        try {
            Method method = bean.getClass().getDeclaredMethod("init");
            method.invoke(bean);
        } catch (NoSuchMethodException e) {
          //  e.printStackTrace();
        }
    }


    private Object createBenchmarkProxy(Object bean) {
        if (isAnnotatedMethodPresentInBean(bean, Benchmark.class)) {
            return wrapBeanWithBenchmarkProxy(bean);
        }
        return bean;
    }


    private boolean isAnnotatedMethodPresentInBean(Object bean, Class clazz) {
        for (Method method: bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(clazz)) {
                return true;
            }
        }
        return false; // there is no clazz annotation present in this bean
    }


    private Object wrapBeanWithBenchmarkProxy(Object bean) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class[] interfaces = bean.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, new BenchmarkProxyHandler(bean));
    }

}




