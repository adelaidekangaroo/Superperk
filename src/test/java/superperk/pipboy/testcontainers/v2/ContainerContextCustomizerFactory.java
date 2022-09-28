package superperk.pipboy.testcontainers.v2;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;

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
                            if (i instanceof SpringContainerImage) {
                                parameters.add(i);
                            } else if (i instanceof SpringContainerReuse) {
                                parameters.add(i);
                            }
                        });
                        containerAnnotations.put(beanName, parameters);
                    }
                });
        return new ContainerContextCustomizer(containerAnnotations);
    }
}