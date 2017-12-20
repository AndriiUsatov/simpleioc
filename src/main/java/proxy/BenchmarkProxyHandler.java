package proxy;

import ioc.Benchmark;
import org.apache.commons.lang3.time.StopWatch;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BenchmarkProxyHandler implements InvocationHandler {
    private Object bean;
    private StopWatch stopWatch = new StopWatch();

    public BenchmarkProxyHandler(Object bean) {
        this.bean = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method = bean.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

        if (method.isAnnotationPresent(Benchmark.class)) {
            stopWatch.start();
            Object retValue = method.invoke(bean, args);
            stopWatch.stop();
            System.out.println("Method call was finished in " + stopWatch.getTime(TimeUnit.MILLISECONDS) + " milliseconds");
            stopWatch.reset();
            return retValue;
        }
        return method.invoke(bean, args);
    }
}
