package superperk.pipboy.testcontainers2.definitions;

public final class MongoContainerDefinition extends AbstractContainerDefinition {
    public MongoContainerDefinition(String image) {
        super(ContainerType.MONGO, image);
    }
}