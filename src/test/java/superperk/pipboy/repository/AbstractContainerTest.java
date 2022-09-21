package superperk.pipboy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import superperk.pipboy.ApplicationTest;

public class AbstractContainerTest extends ApplicationTest {

    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        var postgresDockerImage = DockerImageName.parse("postgres:14.5");
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(postgresDockerImage);
        POSTGRES_CONTAINER.start();
    }

    /**
     * Set random port, username and password for db.
     *
     * @param registry - for re-write properties.
     */
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
    }

    @Test
    void container_must_be_launched() {
        Assertions.assertTrue(POSTGRES_CONTAINER.isRunning());
    }

}