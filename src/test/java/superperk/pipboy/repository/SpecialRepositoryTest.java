package superperk.pipboy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest // for load context
@Testcontainers // manage life cycle testcontainers
@ActiveProfiles("test") // properties from application-test.properties
class SpecialRepositoryTest {

    private static final DockerImageName POSTGRES_IMAGE_NAME = DockerImageName.parse("postgres:14.5");
    @Container
    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);
    @Autowired
    private SpecialRepository specialRepository;

    /**
     * Set random port, username and password for db.
     *
     * @param registry - for re-write properties.
     */
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
    }

    @Test
    void should_return_all_specials() {
        specialRepository.findAll().forEach(System.out::println);
    }
}