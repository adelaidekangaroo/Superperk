package superperk.pipboy.testcontainers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

//@Component
@ContainerDependencies(initBefore = DataSource.class)
public class PostgresContainer implements Container {

    private PostgreSQLContainer<?> basicPostgresSQLContainer;

    //@Autowired
   // private Environment environment;

    private String version = "postgres:latest";
    private List<String> profiles = List.of();
    private boolean reusable = false;

    public PostgresContainer() {
        System.out.println("CONSTRUCTOR MY BEAN");
    }

    public PostgresContainer(String version) {
        this.version= version;
        System.out.println("CONSTRUCTOR MY BEAN");
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("MY BEAN INIT METHOD");
        basicPostgresSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse(version))
                .withReuse(reusable = isReuse());
        //basicPostgresSQLContainer.start();

        System.setProperty("spring.datasource.url", basicPostgresSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", basicPostgresSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", basicPostgresSQLContainer.getPassword());

        System.out.println("IS REUSE - " + reusable);
        System.out.println("VERSION - " + version);
    }

    public void start() {
        basicPostgresSQLContainer.start();
    }

    @PreDestroy
    public void kill() {
        System.out.println("MY BEAN DESTR METHOD");
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setProfiles(String[] profiles) {
        this.profiles = Arrays.stream(profiles).toList();
    }

    /**
     * Находит совпадение между заданным для переспользования контейнера профилями,
     * и активными.
     */
    private boolean isReuse() {
return false;
       // return profiles.stream()
         //       .anyMatch(getActiveProfiles()::contains);
    }

/*
  //  private List<String> getActiveProfiles() {
        return Arrays.stream(environment.getActiveProfiles()).toList();
    }
*/
}