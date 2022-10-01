package superperk.pipboy.testcontainers.v3.containers;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import superperk.pipboy.testcontainers.annotations.ContainerDependencies;

import javax.sql.DataSource;

@Component
@ContainerDependencies(initBefore = DataSource.class)
public final class PostgresContainer extends AbstractGenericContainer implements Container {

    private PostgreSQLContainer<?> sourceContainer;

    @Override
    public void init() {
        sourceContainer = new PostgreSQLContainer<>(DockerImageName.parse(version));
        sourceContainer.withReuse(reuse);
        sourceContainer.start();

        System.setProperty("spring.datasource.url", sourceContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", sourceContainer.getUsername());
        System.setProperty("spring.datasource.password", sourceContainer.getPassword());
    }

    @Override
    protected GenericContainer getSourceContainer() {
        return sourceContainer;
    }
}