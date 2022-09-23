package superperk.pipboy.testcontainers.v2;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import superperk.pipboy.testcontainers.ContainerImage;
import superperk.pipboy.testcontainers.ContainerReused;
import superperk.pipboy.testcontainers.SpringTestContainer;
import superperk.pipboy.testcontainers.SpringTestContainers;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class SettingContainerBeanPostProcessor implements BeanPostProcessor {



    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization(@NotNull Object bean " + beanName +", @NotNull String beanName) throws BeansException {");

        // if (bean.getClass().isAnnotationPresent(SpringTestContainers.class)) {

            for (Field field : bean.getClass().getDeclaredFields()) {

                try {
                   if (Arrays.stream(field.getType().getInterfaces()).anyMatch(i -> {
                        return i == Container.class;
                    })) {
                       field.setAccessible(true);
                       System.out.println("CONTAINERS IS TRUE!!");
                    var object = field.get(bean);
                    System.out.println("BPP - " + object.getClass());
                    if (object instanceof Container springTestContainer) {
                        System.out.println("BPP - " + "Container springTestContainer");
                        if (field.isAnnotationPresent(ContainerImage.class)) {
                            var containerImage = field.getAnnotation(ContainerImage.class);
                            springTestContainer.setVersion(containerImage.image());
                        }
                        if (field.isAnnotationPresent(ContainerReused.class)) {
                            var containerReused = field.getAnnotation(ContainerReused.class);
                            springTestContainer.setProfiles(containerReused.byProfiles());
                        }
                    }
                   }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
      //  }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("postgresContainer")) {
            var o = (PostgresContainer) bean;
            ((PostgresContainer) bean).start();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}