package superperk.pipboy.testcontainers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import superperk.pipboy.testcontainers.annotations.ContainerImage;
import superperk.pipboy.testcontainers.annotations.ContainerReuse;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerImageParameter;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerParameter;
import superperk.pipboy.testcontainers.handlers.parameters.ContainerReuseParameter;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Получение доступа к спринг контексту, к активным профилям через окружение, и регистрация BeanFactoryPostProcessor
 */
@Slf4j
public class ContainerContextCustomizer implements ContextCustomizer {

    private final Map<String, List<ContainerParameter>> containerParameters = new HashMap<>();
    private final Map<String, List<Annotation>> containerAnnotations;

    public ContainerContextCustomizer(Map<String, List<Annotation>> containerAnnotations) {
        this.containerAnnotations = containerAnnotations;
    }

    @Override
    public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
        ConfigurableEnvironment environment = context.getEnvironment();
        containerAnnotations.forEach((k, v) -> {
            List<ContainerParameter> parameters = new ArrayList<>();
            v.forEach(annotation -> {
                if (annotation instanceof ContainerImage containerImage) {
                    parameters.add(new ContainerImageParameter(containerImage.image()));
                } else if (annotation instanceof ContainerReuse containerReuse) {
                    parameters.add(new ContainerReuseParameter(
                            isReuse(
                                    environment.getActiveProfiles(),
                                    containerReuse.byProfiles()
                            )
                    ));
                }
            });
            containerParameters.put(k, parameters);
        });
        context.addBeanFactoryPostProcessor(new ContainerBeanFactoryPostProcessor(containerParameters));
    }

    private boolean isReuse(String[] activeProfiles, String[] reuseProfiles) {
        return Arrays.stream(activeProfiles)
                .toList()
                .stream()
                .anyMatch(Arrays.stream(reuseProfiles).toList()::contains);
    }
}