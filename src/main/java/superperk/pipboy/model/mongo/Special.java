package superperk.pipboy.model.mongo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@EqualsAndHashCode
@Document(collection = "specials")
public final class Special {
    @Id
    private String id;
    private String title;
    private String description;
}