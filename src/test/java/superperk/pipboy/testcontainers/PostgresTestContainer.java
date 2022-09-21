package superperk.pipboy.testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
public class PostgresTestContainer implements SpringTestContainer {

    @Autowired
    private Environment environment;

    private String version = "postgres:latest";
    private String[] profiles = {};
    private boolean reusable = false;
    private PostgreSQLContainer<?> container;

    public void start() {
        container = new PostgreSQLContainer<>(DockerImageName.parse(version))
                .withReuse(reusable = isReuse());
        container.start();
        prepareProperties();
    }

    @PreDestroy
    public void stop() {
        if (!reusable) container.stop();
    }

    private void prepareProperties() {
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    /**
     * Находит совпадение между заданным для переспользования контейнера профилями,
     * и активными.
     */
    private boolean isReuse() {
        return Arrays.stream(profiles)
                .toList()
                .stream()
                .anyMatch(Arrays.stream(environment.getActiveProfiles()).toList()::contains);
    }

    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getProfiles() {
        return profiles;
    }

    @Override
    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

    public PostgreSQLContainer<?> getContainer() {
        return container;
    }
}