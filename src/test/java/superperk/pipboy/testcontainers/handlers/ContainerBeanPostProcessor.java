package superperk.pipboy.testcontainers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import superperk.pipboy.testcontainers.containers.Container;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerImageParameter;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerParameter;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerReuseParameter;

import java.util.List;
import java.util.Map;

public class ContainerBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, List<ContainerParameter>> containerParameters;

    public ContainerBeanPostProcessor(Map<String, List<ContainerParameter>> containerParameters) {
        this.containerParameters = containerParameters;
    }

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        var b = containerParameters.containsKey(beanName);
        if (b && bean instanceof Container container) {
            containerParameters.get(beanName).forEach(i -> {
                if (i instanceof ContainerImageParameter cip) {
                    container.setVersion(cip.image());
                } else if (i instanceof ContainerReuseParameter crp) {
                    container.setReuse(crp.reuse());
                }
            });
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}