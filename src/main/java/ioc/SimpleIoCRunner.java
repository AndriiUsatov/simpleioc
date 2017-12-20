package ioc;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.RepoBean;
import repository.RepoBeanInterface;

import java.util.HashMap;
import java.util.Map;

public class SimpleIoCRunner {
    public static void main(String[] args) {
//        Map<String, Class<?>> beanDescription = new HashMap<String, Class<?>>() {{
//            put("repoBean", RepoBean.class);
//        }};
//
//        Config config = new JavaConfig(beanDescription);
//        SimpleIoC simpleIoC = new SimpleIoC(config);
//        RepoBeanInterface repoBean = (RepoBeanInterface) simpleIoC.getBean("repoBean");
//        repoBean.calculate();


        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        RepoBeanInterface repoBean = (RepoBeanInterface) context.getBean("repoBean");
        System.out.println(repoBean.calculate());
    }
}
