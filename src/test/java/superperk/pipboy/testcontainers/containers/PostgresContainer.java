package superperk.pipboy.testcontainers.containers;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import superperk.pipboy.testcontainers.annotations.ContainerDependencies;

import javax.sql.DataSource;

@Component
@ContainerDependencies(initBefore = DataSource.class)
public class PostgresContainer extends AbstractGenericContainer implements Container {

    private PostgreSQLContainer<?> container;

    @Override
    public void init() {
        container = new PostgreSQLContainer<>(DockerImageName.parse(version));
        container.withReuse(reuse);
        container.start();

        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    protected GenericContainer getContainer() {
        return container;
    }
}