package superperk.pipboy.testcontainers.v3;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import superperk.pipboy.testcontainers.containers.PostgresContainer;

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
            switch (abstractContainerDefinition.getContainerType()) {
                case POSTGRES -> {
                    var postgresContainer = (PostgresContainer) bean;
                    var postgresContainerDefinition = (PostgresContainerDefinition) abstractContainerDefinition;
                    postgresContainer.setVersion(postgresContainerDefinition.getImage());
                }
            }
        });
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private Optional<AbstractContainerDefinition> findAbstractContainerDefinitionByBeanName(String beanName) {
        return abstractContainerDefinitions.stream()
                .filter(abstractContainerDefinition -> abstractContainerDefinition.getContainerType().getBeanName().equals(beanName))
                .findFirst();
    }
}