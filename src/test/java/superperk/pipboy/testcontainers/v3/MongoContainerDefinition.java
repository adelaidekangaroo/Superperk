package superperk.pipboy.testcontainers.v3;

public final class MongoContainerDefinition extends AbstractContainerDefinition {
    public MongoContainerDefinition(String image) {
        super(null, image);
    }
}