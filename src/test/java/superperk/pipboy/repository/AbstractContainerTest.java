package superperk.pipboy.repository;

import superperk.pipboy.ApplicationTest;

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
 * import superperk.pipboy.testcontainers.annotations.SpringContainerImage;import superperk.pipboy.testcontainers.annotations.SpringContainerReuse;@SpringTestContainers public class AbstractContainerTest extends ApplicationTest {
 * @Autowired
 * @SpringContainerImage(image = "postgres:14.5")
 * @SpringContainerReuse(byProfiles = "test")
 * public PostgresTestContainer postgresTestContainer;
 * }
 *}
 */
//@SpringTestContainers
class AbstractContainerTest extends ApplicationTest {


}