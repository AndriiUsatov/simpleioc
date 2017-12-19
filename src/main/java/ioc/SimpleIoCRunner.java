package ioc;

import repository.RepoBean;
import repository.RepoBeanInterface;

import java.util.HashMap;
import java.util.Map;

public class SimpleIoCRunner {
    public static void main(String[] args) {
        Map<String, Class<?>> beanDescriptions = new HashMap<String, Class<?>>() {
            {
                put("repoBean", RepoBean.class);
            }
        };

        Config config = new JavaConfig(beanDescriptions);
        //*repoBean*
        //RepoBean.class
        SimpleIoC ioC = new SimpleIoC(config);
        RepoBeanInterface repoBean = (RepoBeanInterface) ioC.getBean("repoBean");
        System.out.println(repoBean.calculate());
    }
}
