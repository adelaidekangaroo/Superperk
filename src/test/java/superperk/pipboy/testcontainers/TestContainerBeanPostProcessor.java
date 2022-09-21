package superperk.pipboy.testcontainers;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class TestContainerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(SpringTestContainers.class)) {

            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    var object = field.get(bean);

                    if (object instanceof SpringTestContainer springTestContainer) {
                        if (field.isAnnotationPresent(ContainerImage.class)) {
                            var containerImage = field.getAnnotation(ContainerImage.class);
                            springTestContainer.setVersion(containerImage.image());
                        }
                        if (field.isAnnotationPresent(ContainerReused.class)) {
                            var containerReused = field.getAnnotation(ContainerReused.class);
                            springTestContainer.setProfiles(containerReused.byProfiles());
                        }
                        springTestContainer.start();
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}