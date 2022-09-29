package superperk.pipboy.testcontainers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import superperk.pipboy.testcontainers.containers.Container;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerImageSetting;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerReuseSetting;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerSetting;

import java.util.List;
import java.util.Map;

/**
 * Задает настройки из ContainerSettings конкретному бину контейнеру
 */
public final class ContainerBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, List<ContainerSetting>> containerParameters;

    public ContainerBeanPostProcessor(Map<String, List<ContainerSetting>> containerParameters) {
        this.containerParameters = containerParameters;
    }

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (containerParameters.containsKey(beanName)) {
            var container = (Container) bean;
            containerParameters.get(beanName)
                    .forEach(containerSetting -> {
                        if (containerSetting instanceof ContainerImageSetting cip) {
                            container.setVersion(cip.image());
                        } else if (containerSetting instanceof ContainerReuseSetting crp) {
                            container.setReuse(crp.reuse());
                        }
                    });
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}