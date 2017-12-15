package ioc;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SimpleIoCTest {

    private Config config;
    private BeanDefinition beanDefinition;
    private String beanName;

    @Before
    public void setUp() {
        beanName = "beanName";
        Class<?> beanClass = TestBean.class;

        beanDefinition = new BeanDefinition() {
            @Override
            public String getBeanName() {
                return beanName;
            }

            @Override
            public Class getBeanClass() {
                return beanClass;
            }
        };

        config = new Config() {
            @Override
            public List<String> beanNames() {
                return Collections.emptyList();
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {
                return beanDefinition;
            }
        };
    }

    @Test
    public void createContainer() {
        new SimpleIoC(config);
    }

    @Test
    public void beanDefShouldBeEmpty() {
        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();
        assertEquals(Collections.emptyList(), beanDefinitions);
    }

    @Test
    public void beanDefWithOneBeanInConfig() {
        String beanName = "beanName";

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {
                return beanDefinition;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();

        assertEquals(Arrays.asList(beanName), beanDefinitions);
    }

    @Test
    public void beanDefWithSeveralBeansInCongig() {
        String beanName1 = "bean1";
        String beanName2 = "bean2";

        Config config = new Config() {
            @Override
            public BeanDefinition getDefinition(String beanName) {
                return beanDefinition;
            }

            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName1, beanName2);
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();

        assertEquals(Arrays.asList(beanName1, beanName2), beanDefinitions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void beanDefNamesInConfigShouldBeUnique() {
        String beanName1 = "bean1";
        String beanName2 = "bean1";

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName1, beanName2);
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {
                return null;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
    }

    @Test
    public void getBeanWithOneBeanInConfig() {
        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {
                return beanDefinition;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        TestBean testBean = (TestBean) simpleIoC.getBean(beanName);

        assertNotNull(testBean);
    }

    @Test
    public void getBeanSeveralTimes() {
        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {
                return beanDefinition;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);

        TestBean testBean1 = (TestBean) simpleIoC.getBean(beanName);
        TestBean testBean2 = (TestBean) simpleIoC.getBean(beanName);

        assertSame(testBean1, testBean2);
    }

    @Test
    public void getBeanWithDependencies() {
        String testBeanName = "testBean";
        Class<?> testBeanClass = TestBean.class;

        String testDependentBeanName = "dependentBean";
        Class<?> testDependentBeanClass = TestBeanWithDependency.class;

        Config config = new Config() {

            @Override
            public List<String> beanNames() {
                return Arrays.asList(testBeanName, testDependentBeanName);
            }

            @Override
            public BeanDefinition getDefinition(String beanName) {

                Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
                beanDefinitionMap.put(testBeanName, new BeanDefinition() {
                    @Override
                    public String getBeanName() {
                        return testBeanName;
                    }

                    @Override
                    public Class getBeanClass() {
                        return testBeanClass;
                    }
                });
                beanDefinitionMap.put(testDependentBeanName, new BeanDefinition() {
                    @Override
                    public String getBeanName() {
                        return testDependentBeanName;
                    }

                    @Override
                    public Class getBeanClass() {
                        return testDependentBeanClass;
                    }
                });

                return beanDefinitionMap.get(beanName);
            }
        }; //Config

        SimpleIoC simpleIoC = new SimpleIoC(config);

        assertNotNull(simpleIoC.getBean(testDependentBeanName));
    }

}

class TestBean {
}

class TestBeanWithDependency {
    public final TestBean testBean;

    TestBeanWithDependency(TestBean testBean) {
        this.testBean = testBean;
    }
}

