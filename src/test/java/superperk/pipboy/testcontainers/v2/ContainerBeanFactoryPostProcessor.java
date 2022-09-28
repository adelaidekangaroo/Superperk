package superperk.pipboy.testcontainers.v2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.beans.Introspector.decapitalize;

public class ContainerBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    private final Map<String, List<ContainerParameter>> containerParameters;

    public ContainerBeanFactoryPostProcessor(Map<String, List<ContainerParameter>> containerParameters) {
        this.containerParameters = containerParameters;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Arrays.stream(beanFactory.getBeanNamesForAnnotation(SpringContainerDependencies.class)).forEach(i -> {
            var myBeanAnn = beanFactory.findAnnotationOnBean(i, SpringContainerDependencies.class);
            var myFirstInitBeanClass = beanFactory.getType(i);
            var mySecondInitBeanClass = myBeanAnn.initBefore();
            Arrays.stream(mySecondInitBeanClass).forEach(d -> {
                beanFactory.getBeanDefinition(decapitalize(d.getSimpleName()))
                        .setDependsOn(decapitalize(myFirstInitBeanClass.getSimpleName()));
            });

        });
        beanFactory.addBeanPostProcessor(new ContainerBeanPostProcessor(containerParameters));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}