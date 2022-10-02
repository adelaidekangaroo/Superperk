package superperk.pipboy.testcontainers.v3;

import lombok.Getter;
import superperk.pipboy.testcontainers.v3.containers.MongoContainer;
import superperk.pipboy.testcontainers.v3.containers.PostgresContainer;

import java.beans.Introspector;

@Getter
public abstract class AbstractContainerDefinition {

    protected ContainerType containerType;
    protected String image;

    public AbstractContainerDefinition(ContainerType containerType, String image) {
        this.containerType = containerType;
        this.image = image;
    }

    @Getter
    public enum ContainerType {
        POSTGRES(PostgresContainer.class),
        MONGO(MongoContainer.class);
       /*  REDIS(PostgresContainer.class),
        RABBIT(PostgresContainer.class),
        KAFKA(PostgresContainer.class);*/

        private final Class<?> beanType;
        private final String beanName;

        ContainerType(Class<?> beanType) {
            this.beanType = beanType;
            this.beanName = Introspector.decapitalize(beanType.getSimpleName());
        }
    }
}