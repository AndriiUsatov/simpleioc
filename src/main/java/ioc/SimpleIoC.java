package ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class SimpleIoC {

    private final Config config;
    private final Map<String, Object> beans;
    private final Set<String> addedBeans;

    public SimpleIoC(Config config) {
        this.config = config;
        beans = new HashMap<>();
        addedBeans = new HashSet<>();
        init();
    }

    public List<String> beanDefinitions() {
        return config.beanNames();
    }

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }

    private void init() {
        if (namesIsNotUnique(config.beanNames()))
            throw new IllegalArgumentException("Definition names should be unique");
        addBeans();
    }

    private void addBeans() {
        try {

            for (String beanName : config.beanNames()) {
                if (addedBeans.contains(beanName))
                    continue;

                Class<?> someClass = config.getDefinition(beanName).getBeanClass();
                Constructor<?> constructor = someClass.getDeclaredConstructors()[0];
                if (constructor.getParameterCount() == 0)
                    addBeanWithoutParameters(beanName);
                else
                    addBeanWithParameters(beanName, constructor);
                addedBeans.add(beanName);
            }
        } catch (Exception e) {}
    }

    private void addBeanWithoutParameters(String beanName) throws Exception {
        Object bean = config.getDefinition(beanName).getBeanClass().newInstance();
        callInitMethod(bean);
        beans.put(beanName, bean);
    }

    private void addBeanWithParameters(String beanName, Constructor<?> constructor) throws Exception {
        List params = new ArrayList();
        Parameter[] parameters = constructor.getParameters();
        for (Parameter parameter : parameters) {
            String keyClassName = getParameterClassName(parameter);
            if (!beans.containsKey(keyClassName)) {
                addBeanWithoutParameters(keyClassName);
                addedBeans.add(keyClassName);
            }
            params.add(beans.get(keyClassName));
        }
        Object bean = constructor.newInstance(params.toArray(new Object[params.size()]));
        callInitMethod(bean);
        beans.put(beanName, bean);
    }

    private boolean namesIsNotUnique(List<String> beanNames) {
        Set<String> names = new HashSet<>(beanNames);
        return beanNames.size() != names.size();
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


}
