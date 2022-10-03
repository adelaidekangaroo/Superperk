package superperk.pipboy.testcontainers2;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import superperk.pipboy.testcontainers2.containers.Container;
import superperk.pipboy.testcontainers2.definitions.AbstractContainerDefinition;

import java.util.List;
import java.util.Optional;

/**
 * Задает настройки из ContainerSettings конкретному бину контейнеру
 */
public final class ContainerBeanPostProcessor implements BeanPostProcessor {

    private final List<AbstractContainerDefinition> abstractContainerDefinitions;

    public ContainerBeanPostProcessor(@NotNull List<AbstractContainerDefinition> abstractContainerDefinitions) {
        this.abstractContainerDefinitions = abstractContainerDefinitions;
    }

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        findAbstractContainerDefinitionByBeanName(beanName).ifPresent(abstractContainerDefinition -> {
            var container = (Container) bean;
            container.setVersion(abstractContainerDefinition.getImage());
        });
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private Optional<AbstractContainerDefinition> findAbstractContainerDefinitionByBeanName(String beanName) {
        return abstractContainerDefinitions.stream()
                .filter(abstractContainerDefinition -> abstractContainerDefinition.getContainerType().getBeanName().equals(beanName))
                .findFirst();
    }
}