package superperk.pipboy.testcontainers.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import superperk.pipboy.testcontainers.annotations.ContainerImage;
import superperk.pipboy.testcontainers.annotations.ContainerReuse;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerImageSetting;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerReuseSetting;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerSetting;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Получение доступа к спринг контексту, к активным профилям через окружение, и регистрация BeanFactoryPostProcessor
 */
public class ContainerContextCustomizer implements ContextCustomizer {

    private final Map<String, List<ContainerSetting>> CONTAINER_SETTINGS = new HashMap<>();
    private final Map<String, List<Annotation>> containerAnnotations;

    public ContainerContextCustomizer(@NotNull Map<String, List<Annotation>> containerAnnotations) {
        this.containerAnnotations = containerAnnotations;
    }

    @Override
    public void customizeContext(@NotNull ConfigurableApplicationContext context,
                                 @NotNull MergedContextConfiguration mergedConfig) {
        containerAnnotations.forEach((beanName, containerAnnotationsByBean) -> {
            List<ContainerSetting> settings = containerAnnotationsByBean.stream()
                    .filter(annotation ->
                            annotation instanceof ContainerImage ||
                                    annotation instanceof ContainerReuse)
                    .map(annotation -> {
                        if (annotation instanceof ContainerImage containerImage) {
                            return new ContainerImageSetting(containerImage.image());
                        } else if (annotation instanceof ContainerReuse containerReuse) {
                            return new ContainerReuseSetting(
                                    isReuse(context.getEnvironment().getActiveProfiles(), containerReuse.byProfiles())
                            );
                        } else {
                            throw new IllegalArgumentException();
                        }
                    })
                    .collect(Collectors.toList());
            CONTAINER_SETTINGS.put(beanName, settings);
        });
        context.addBeanFactoryPostProcessor(new ContainerBeanFactoryPostProcessor(CONTAINER_SETTINGS));
    }

    private boolean isReuse(String[] activeProfiles, String[] reuseProfiles) {
        return Arrays.stream(activeProfiles)
                .toList()
                .stream()
                .anyMatch(Arrays.stream(reuseProfiles).toList()::contains);
    }
}