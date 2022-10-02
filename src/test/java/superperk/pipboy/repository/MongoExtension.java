package superperk.pipboy.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MongoExtension implements BeforeAllCallback, AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        SpringExtension.getApplicationContext(context); // load context
        var mongoURL = System.getProperty("spring.data.mongodb.uri");
        var testClass = context.getRequiredTestClass();

        try (MongoClient mongoClient = MongoClients.create(mongoURL)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            Arrays.stream(testClass.getDeclaredAnnotationsByType(MongoInsert.class))
                    .forEach(mongoInsert -> {
                        MongoCollection<Document> collection = db.getCollection(mongoInsert.collection());
                        collection.drop();
                        try {
                            URI uri = ClassLoader.getSystemResource(mongoInsert.location()).toURI();
                            var s = new String(Files.readAllBytes(Path.of(uri)));
                            JSONArray inputArray = new JSONArray(s);
                            List<Document> documents = new LinkedList<>();
                            for (int i = 0; i < inputArray.length(); i++) {
                                JSONObject resultObject = inputArray.getJSONObject(i);
                                documents.add(Document.parse(resultObject.toString()));
                            }
                            collection.insertMany(documents);
                        } catch (URISyntaxException | IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {

    }
}