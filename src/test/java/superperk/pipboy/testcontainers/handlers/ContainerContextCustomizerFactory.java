package superperk.pipboy.testcontainers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.ReflectionUtils;
import superperk.pipboy.testcontainers.annotations.ContainerImage;
import superperk.pipboy.testcontainers.annotations.ContainerReuse;
import superperk.pipboy.testcontainers.containers.Container;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 *
 */
public class ContainerContextCustomizerFactory implements ContextCustomizerFactory {

    private final Map<String, List<Annotation>> containerAnnotations = new HashMap<>();

    @Override
    public ContainerContextCustomizer createContextCustomizer(@NotNull Class<?> testClass,
                                                              @NotNull List<ContextConfigurationAttributes> configAttributes) {
        ReflectionUtils.doWithFields(testClass,
                field -> {
                    if (Container.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        var beanName = field.getName();
                        List<Annotation> parameters = new ArrayList<>();
                        Arrays.stream(field.getAnnotations()).toList().forEach(i -> {
                            if (i instanceof ContainerImage) {
                                parameters.add(i);
                            } else if (i instanceof ContainerReuse) {
                                parameters.add(i);
                            }
                        });
                        containerAnnotations.put(beanName, parameters);
                    }
                });
        return new ContainerContextCustomizer(containerAnnotations);
    }
}