package superperk.pipboy.testcontainers.v3;

public final class PostgresContainerDefinition extends AbstractContainerDefinition {
    public PostgresContainerDefinition(String image) {
        super(ContainerType.POSTGRES, image);
    }
}