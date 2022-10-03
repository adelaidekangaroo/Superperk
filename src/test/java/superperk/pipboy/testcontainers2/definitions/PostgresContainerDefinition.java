package superperk.pipboy.testcontainers2.definitions;

public final class PostgresContainerDefinition extends AbstractContainerDefinition {
    public PostgresContainerDefinition(String image) {
        super(ContainerType.POSTGRES, image);
    }
}