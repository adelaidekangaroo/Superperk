package superperk.pipboy.testcontainers.v3.containers;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@Component
public final class MongoContainer extends AbstractGenericContainer implements Container {
    private MongoDBContainer sourceContainer;

    @Override
    public void init() {
        sourceContainer = new MongoDBContainer(DockerImageName.parse(version));
        sourceContainer.withReuse(reuse);
        sourceContainer.start();

        System.setProperty("spring.data.mongodb.uri", sourceContainer.getReplicaSetUrl());
    }

    @Override
    protected GenericContainer getSourceContainer() {
        return sourceContainer;
    }
}