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
 * Сканирует тест класс на предмет наличия объявленных контейнеров и собирает настройки по каждому их них.
 */
public class ContainerContextCustomizerFactory implements ContextCustomizerFactory {

    /**
     * Все container аннотации сгруппированные по бинам
     */
    private final Map<String, List<Annotation>> containerAnnotations = new HashMap<>();

    @Override
    public ContainerContextCustomizer createContextCustomizer(@NotNull Class<?> testClass,
                                                              @NotNull List<ContextConfigurationAttributes> configAttributes) {
        ReflectionUtils.doWithFields(testClass, field -> {
            if (Container.class.isAssignableFrom(field.getType())) { // контейнер должен реализовывать Container
                field.setAccessible(true);
                var beanName = field.getName();
                List<Annotation> annotations = new ArrayList<>();
                Arrays.stream(field.getAnnotations()).forEach(annotation -> {
                    if (annotation instanceof ContainerImage) {
                        annotations.add(annotation);
                    } else if (annotation instanceof ContainerReuse) {
                        annotations.add(annotation);
                    }
                });
                containerAnnotations.put(beanName, annotations);
            }
        });
        return new ContainerContextCustomizer(containerAnnotations);
    }
}