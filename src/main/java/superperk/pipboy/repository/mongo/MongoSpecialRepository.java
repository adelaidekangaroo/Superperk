package superperk.pipboy.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import superperk.pipboy.model.mongo.Special;

public interface MongoSpecialRepository extends MongoRepository<Special, String> {
}