package ioc;

import proxy.BenchmarkProxyHandler;

import java.lang.reflect.*;
import java.util.*;

public class SimpleIoC {

    private final Config config;
    private final Map<String, Object> beans;

    public SimpleIoC(Config config) {
        this.config = config;
        beans = new HashMap<>();
        checkForUniqueBeanNames(config.beanNames());
    }

    public List<String> beanDefinitions() {
        return config.beanNames();
    }

    public Object getBean(String beanName) {
        if (!beans.containsKey(beanName)) {
            BeanDefinition beanDefinition = config.getDefinition(beanName);

            try {
                buildBeanFromDefinition(beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return beans.get(beanName);
    }

    private void buildBeanFromDefinition(BeanDefinition beanDefinition) throws Exception {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor constructor = beanClass.getDeclaredConstructors()[0];
        String beanName = beanDefinition.getBeanName();
        Object bean;

        if (isDefaultConstructor(constructor))
            bean = addBeanWithoutParameters(beanName);
        else
            bean = addBeanWithParameters(constructor);

        bean = createBenchmarkProxy(bean);

        beans.put(beanName, bean);
    }


    private boolean isDefaultConstructor(Constructor constructor) {
        return constructor.getParameterCount() == 0;
    }

    private Object addBeanWithoutParameters(String beanName) throws Exception {
        Object bean = config.getDefinition(beanName).getBeanClass().newInstance();
        callInitMethod(bean);
        return bean;
    }

    private Object addBeanWithParameters(Constructor<?> constructor) throws Exception {
        List params = new ArrayList();
        Parameter[] parameters = constructor.getParameters();

        for (Parameter parameter : parameters) {
            String keyClassName = getParameterClassName(parameter);
            params.add(getBean(keyClassName));
        }

        Object bean = constructor.newInstance(params.toArray(new Object[params.size()]));
        callInitMethod(bean);

        return bean;
    }

    private void checkForUniqueBeanNames(List<String> beanNames) {
        Set<String> names = new HashSet<>(beanNames);
        if (beanNames.size() != names.size())
            throw new IllegalArgumentException("Definition names should be unique");
    }

    private String getParameterClassName(Parameter parameter) {
        return parameter.getType().getSimpleName().substring(0, 1).toLowerCase()
                + parameter.getType().getSimpleName().substring(1);
    }

    private void callInitMethod(Object bean) throws Exception {
        Method initMethod = getInitMethod(bean);
        if (initMethod != null) {
            initMethod.invoke(bean);
        }
    }

    private Method getInitMethod(Object bean) {
        Method initMethod;
        try {
            initMethod = bean.getClass().getMethod("init");
        } catch (NoSuchMethodException e) {
            initMethod = null;
        }
        return initMethod;
    }

//    private Object createBenchmarkProxy(Object bean) {
//        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method.getName());
//                return method.invoke(bean, args);
//            }
//        });
//    }
    private Object createBenchmarkProxy(Object bean){
        if(isAnnotatedMethodPresentInBean(bean, Benchmark.class))
            return wrapBeanWithBenchmarkProxy(bean);
        return bean;
    }


    private boolean isAnnotatedMethodPresentInBean(Object bean, Class clazz) {
        for(Method method : bean.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(clazz))
                return true;
        }
        return false;
    }

    private Object wrapBeanWithBenchmarkProxy(Object bean) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Class[] interfaces = bean.getClass().getInterfaces();
        return Proxy.newProxyInstance(classLoader, interfaces, new BenchmarkProxyHandler(bean));
    }


}
