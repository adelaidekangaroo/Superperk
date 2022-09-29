package superperk.pipboy.testcontainers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.ReflectionUtils;
import superperk.pipboy.testcontainers.annotations.ContainerImage;
import superperk.pipboy.testcontainers.annotations.ContainerReuse;
import superperk.pipboy.testcontainers.annotations.SpringContainer;
import superperk.pipboy.testcontainers.containers.Container;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сканирует тест класс на предмет наличия объявленных контейнеров и собирает настройки по каждому их них.
 * Создает ContainerContextCustomizer.
 */
public class ContainerContextCustomizerFactory implements ContextCustomizerFactory {

    /**
     * Все container аннотации сгруппированные по бинам
     */
    private final Map<String, List<Annotation>> CONTAINER_ANNOTATIONS = new HashMap<>();

    @Override
    public ContainerContextCustomizer createContextCustomizer(@NotNull Class<?> testClass,
                                                              @NotNull List<ContextConfigurationAttributes> configAttributes) {
        ReflectionUtils.doWithFields(
                // test class and all its parents
                testClass,
                // invoke
                field -> {
                    field.setAccessible(true);
                    var beanNameFromSpringContainerAnnotation = field.getAnnotation(SpringContainer.class).beanName();
                    var beanName = beanNameFromSpringContainerAnnotation.isEmpty()
                            ? Introspector.decapitalize(field.getType().getSimpleName())
                            : beanNameFromSpringContainerAnnotation;
                    List<Annotation> containerAnnotationsByBean = Arrays.stream(field.getAnnotations())
                            .filter(annotation ->
                                    annotation instanceof ContainerImage ||
                                            annotation instanceof ContainerReuse)
                            .toList();
                    CONTAINER_ANNOTATIONS.put(beanName, containerAnnotationsByBean);
                },
                // filter
                field -> Container.class.isAssignableFrom(field.getType())
                        && field.isAnnotationPresent(SpringContainer.class)
        );
        return new ContainerContextCustomizer(CONTAINER_ANNOTATIONS);
    }
}