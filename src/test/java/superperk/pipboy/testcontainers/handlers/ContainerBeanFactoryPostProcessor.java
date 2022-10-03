package superperk.pipboy.testcontainers.handlers;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerSetting;
import superperk.pipboy.testcontainers2.annotations.ContainerDependencies;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Определение порядка создая бинов и регистрация ContainerBeanPostProcessor
 */
public final class ContainerBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    private final Map<String, List<ContainerSetting>> containerParameters;

    public ContainerBeanFactoryPostProcessor(Map<String, List<ContainerSetting>> containerParameters) {
        this.containerParameters = containerParameters;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        var containerBeanNames = beanFactory.getBeanNamesForAnnotation(ContainerDependencies.class);
        Arrays.stream(containerBeanNames)
                .forEach(containerBeanName ->
                        Arrays.stream(beanFactory.findAnnotationOnBean(containerBeanName, ContainerDependencies.class).initBefore())
                                .forEach(dependentBeanType -> {
                                    var beanNamesByDependentBeanType = beanFactory.getBeanNamesForType(dependentBeanType);
                                    Arrays.stream(beanNamesByDependentBeanType)
                                            .map(beanFactory::getBeanDefinition)
                                            .forEach(beanDefinitionByDependentBeanType ->
                                                    beanDefinitionByDependentBeanType.setDependsOn(containerBeanName));
                                }));
        beanFactory.addBeanPostProcessor(new ContainerBeanPostProcessor(containerParameters));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}