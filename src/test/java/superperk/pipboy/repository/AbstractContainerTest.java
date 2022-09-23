package superperk.pipboy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import superperk.pipboy.ApplicationTest;
import superperk.pipboy.testcontainers.ContainerImage;
import superperk.pipboy.testcontainers.ContainerReused;
import superperk.pipboy.testcontainers.PostgresTestContainer;
import superperk.pipboy.testcontainers.SpringTestContainers;

/**
 * Аs it was before -
 * {@snippet :
 * public class AbstractContainerTest extends ApplicationTest {
 *    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER;
 *
 *     static {
 *         var postgresDockerImage = DockerImageName.parse("postgres:14.5");
 *         POSTGRES_CONTAINER = new PostgreSQLContainer<>(postgresDockerImage);
 *         POSTGRES_CONTAINER.start();
 *     }
 *
 *     @DynamicPropertySource
 *     static void overrideProperties(DynamicPropertyRegistry registry) {
 *         registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
 *         registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
 *         registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
 *     }
 * }
 *}
 * How is it now -
 * {@snippet :
 *
 * @SpringTestContainers public class AbstractContainerTest extends ApplicationTest {
 * @Autowired
 * @ContainerImage(image = "postgres:14.5")
 * @ContainerReused(byProfiles = "test")
 * public PostgresTestContainer postgresTestContainer;
 * }
 *}
 */
//@SpringTestContainers
class AbstractContainerTest extends ApplicationTest {

    @Autowired
    @ContainerImage(image = "postgres:14.5")
    @ContainerReused(byProfiles = "test")
    public PostgresTestContainer postgresTestContainer;

  //  @Test
    void container_must_be_launched() {
        Assertions.assertTrue(postgresTestContainer.getContainer().isRunning());
    }

}