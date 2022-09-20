package superperk.pipboy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest // for load context
@ActiveProfiles("test") // properties from application-test.properties
public class AbstractContainerTest {

    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER;
    private static final DockerImageName POSTGRES_IMAGE_NAME = DockerImageName.parse("postgres:14.5");

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);
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