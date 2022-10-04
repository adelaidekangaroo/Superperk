package superperk.pipboy.model.mongo;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@ToString
@EqualsAndHashCode
@Document(collection = "specials")
public final class Special {
    @Id
    @EqualsAndHashCode.Exclude
    private String id;
    private String title;
    private String description;
}