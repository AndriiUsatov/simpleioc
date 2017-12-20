package ioc;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SimpleIoCTest {

    static class TestBean {}

    static class TestBeanWithDependency {
        public final TestBean testBean;
//        public final TestBeanWithDependency beanWithDependency;

        public TestBeanWithDependency(TestBean testBean) {
            this.testBean = testBean;
        }

//        public TestBeanWithDependency(RepoBean testBean, TestBeanWithDependency testBeanWithDependency){
//            this.testBean = testBean;
//            this.beanWithDependency = testBeanWithDependency;
//        }
    }

    private Config config = new Config() {
        @Override
        public List<String> beanNames() {
            return Collections.emptyList();
        }

        @Override
        public BeanDefinition beanDefinition(String beanName) {
            return null;
        }
    };

    @Test
    public void createIoCContainer() {
        new SimpleIoC(config);
    }

    @Test
    public void containerBeanDefinitionsShouldBeEmpty() {
        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();
        assertEquals(Collections.emptyList(), beanDefinitions);
    }

    @Test
    public void beanDefinitionWithOneBeanConfig() {
        String beanName = "superBean";

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {
                return null;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();

        assertEquals(Arrays.asList(beanName), beanDefinitions);
    }

    @Test
    public void beanDefinitionWithSeveralBeansConfig() {
        String beanName1 = "superBean1";
        String beanName2 = "superBean2";

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName1, beanName2);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {
                return null;
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        List<String> beanDefinitions = simpleIoC.beanDefinitions();

        assertEquals(Arrays.asList(beanName1, beanName2), beanDefinitions);
    }


    @Test(expected = IllegalArgumentException.class)
    public void beanNamesInConfigShouldBeUnique() {
        String beanName1 = "superBean1";
        String beanName2 = "superBean1";

        Config config = new Config() {

            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName1, beanName2);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {
                return new BeanDefinition() {
                    @Override
                    public String getBeanName() {
                        return beanName;
                    }

                    @Override
                    public Class<?> getBeanClass() {
                        return TestBean.class;
                    }
                };
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
    }

    @Test
    public void getBeanWithOneBeanInConfig() {

        String beanName = "beanName";
        Class<?> beanClass = TestBean.class;

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {
                return new BeanDefinition() {
                    @Override
                    public String getBeanName() {
                        return beanName;
                    }

                    @Override
                    public Class getBeanClass() {
                        return beanClass;
                    }
                };
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        TestBean testBean = (TestBean) simpleIoC.getBean(beanName);
        assertNotNull(testBean);
    }

    @Test
    public void getSameBeanSeveralTimes() {
        String beanName = "bean";
        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(beanName);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {
                return new BeanDefinition() {
                    @Override
                    public String getBeanName() {
                        return beanName;
                    }

                    @Override
                    public Class getBeanClass() {
                        return TestBean.class;
                    }
                };
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        TestBean testBean1 = (TestBean)simpleIoC.getBean(beanName);
        TestBean testBean2 = (TestBean)simpleIoC.getBean(beanName);

        assertSame(testBean1, testBean2);
    }


    @Test
    public void getBeanWithDependencies() throws Exception {

        String testBeanName = "testBean";
        Class<?> testBeanClass = TestBean.class;

        String dependantBeanName = "testBeanWithDependency";
        Class<?> dependantBeanClass = TestBeanWithDependency.class;

        Config config = new Config() {
            @Override
            public List<String> beanNames() {
                return Arrays.asList(testBeanName, dependantBeanName);
            }

            @Override
            public BeanDefinition beanDefinition(String beanName) {

                Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
                beanDefinitions.put(testBeanName,
                        new BeanDefinition() {
                            @Override
                            public String getBeanName() {
                                return testBeanName;
                            }

                            @Override
                            public Class<?> getBeanClass() {
                                return testBeanClass;
                            }
                        }
                );

                beanDefinitions.put(dependantBeanName,
                        new BeanDefinition() {
                            @Override
                            public String getBeanName() {
                                return dependantBeanName;
                            }

                            @Override
                            public Class<?> getBeanClass() {
                                return dependantBeanClass;
                            }
                        }
                );

                return beanDefinitions.get(beanName);
            }
        };

        SimpleIoC simpleIoC = new SimpleIoC(config);
        TestBeanWithDependency testBeanWithDependency = (TestBeanWithDependency) simpleIoC.getBean(dependantBeanName);
        TestBean testBean = testBeanWithDependency.testBean;

        assertNotNull(testBean);

        TestBean injectedTestBean = (TestBean) simpleIoC.getBean(testBeanName);
        assertSame(injectedTestBean, testBean);
    }
}