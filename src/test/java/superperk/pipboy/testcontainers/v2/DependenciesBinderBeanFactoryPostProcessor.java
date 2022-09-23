package superperk.pipboy.testcontainers.v2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.beans.Introspector.decapitalize;

@Component
public class DependenciesBinderBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("DependsOnContainerSetterBFPP --- " + "postProcessBeanFactory");
        Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
        Arrays.stream(beanFactory.getBeanNamesForAnnotation(Contr.class)).forEach(i -> {
            System.out.println("OOOO");
        });

        Arrays.stream(beanFactory.getBeanNamesForAnnotation(ContainerDependencies.class)).forEach(i -> {
            System.out.println("NyAnnBean - " + i);
            //  var myBean = beanFactory.getBean(i);
            var myBeanAnn = beanFactory.findAnnotationOnBean(i, ContainerDependencies.class);
            System.out.println("GET MY BEAN TYPE");
            var myFirstInitBeanClass = beanFactory.getType(i);
            System.out.println(beanFactory.getType(i));
            System.out.println(i);
            var mySecondInitBeanClass = myBeanAnn.initBefore();
            System.out.println(mySecondInitBeanClass);
            Arrays.stream(mySecondInitBeanClass).forEach(d -> {
                beanFactory.getBeanDefinition(decapitalize(d.getSimpleName()))
                        .setDependsOn(decapitalize(myFirstInitBeanClass.getSimpleName()));
            });



        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}