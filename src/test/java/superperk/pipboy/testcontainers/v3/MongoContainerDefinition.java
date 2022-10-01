package superperk.pipboy.testcontainers.v3;

import lombok.Getter;

@Getter
public final class MongoContainerDefinition extends AbstractContainerDefinition {
    public MongoContainerDefinition(String image) {
        super(null, image);
    }
}