package superperk.pipboy.testcontainers.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

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
                if (annotation instanceof SpringContainerImage springContainerImage) {
                    parameters.add(new ContainerImageParameter(springContainerImage.image()));
                } else if (annotation instanceof SpringContainerReuse springContainerReuse) {
                    parameters.add(new ContainerReuseParameter(
                            isReuse(
                                    environment.getActiveProfiles(),
                                    springContainerReuse.byProfiles()
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