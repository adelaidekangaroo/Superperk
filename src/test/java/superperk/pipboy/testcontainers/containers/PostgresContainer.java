package superperk.pipboy.testcontainers.containers;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import superperk.pipboy.testcontainers.annotations.ContainerDependencies;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Component
@ContainerDependencies(initBefore = DataSource.class)
public class PostgresContainer<SELF extends PostgresContainer<SELF>>
        // extends PostgreSQLContainer<SELF>
        implements Container {

    private String version = "postgres:latest";
    private boolean reuse = false;

    private PostgreSQLContainer<?> container;

    @PostConstruct
    public void postConstruct() {
        container = new PostgreSQLContainer<>(DockerImageName.parse(version));
        container.withReuse(reuse);
        container.start();

        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @PreDestroy
    public void preDestroy() {
        if (!reuse) container.stop();
    }

    /* public SELF withDockerImage(String image) {
         if (!image.equals("postgres:latest")) {
             this.setDockerImageName(DockerImageName.parse(version).toString());
         }
         return self();
     }

     public SELF withContainerReuse(boolean reuse) {
         this.withReuse(reuse);
         return self();
     }
 */
    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public void setReuse(boolean reuse) {
        this.reuse = reuse;
    }
}