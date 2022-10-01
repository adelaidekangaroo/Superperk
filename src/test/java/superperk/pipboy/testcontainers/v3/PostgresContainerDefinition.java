package superperk.pipboy.testcontainers.v3;

import lombok.Getter;

@Getter
public final class PostgresContainerDefinition extends AbstractContainerDefinition {
    public PostgresContainerDefinition(String image) {
        super(ContainerType.POSTGRES, image);
    }
}