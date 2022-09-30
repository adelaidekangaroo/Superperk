package superperk.pipboy.repository;

import org.junit.jupiter.api.extension.*;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

public class MongoExtension implements BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor,
        BeforeEachCallback, AfterEachCallback, BeforeTestExecutionCallback, AfterTestExecutionCallback,
        ParameterResolver {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("MongoExtension beforeAll");
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        //  System.out.println("1 " + applicationContext.getBean(DataSource.class));
        System.out.println("from before all" + System.getProperty("spring.datasource.url"));
        System.out.println(context.getRequiredTestClass());
        var testClass = context.getRequiredTestClass();
        if (testClass.isAnnotationPresent(MongoInserts.class)) {
            var mongoData = testClass.getAnnotation(MongoInserts.class);
            Arrays.stream(mongoData.value()).toList().forEach(i -> {
                System.out.println("Populate location");
                System.out.println(i.location());
                System.out.println(i.collection());
            });
        }
        /*try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            try {
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("title", "Ski Bloopers")
                        .append("genres", Arrays.asList("Documentary", "Comedy")));
                System.out.println("Success! Inserted document id: " + result.getInsertedId());
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }
        }*/
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        System.out.println("MongoExtension beforeEach");

        System.out.println(System.getProperty("spring.datasource.url"));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("MongoExtension afterAll");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("MongoExtension afterEach(");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("MongoExtension afterTestExecution");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.println("MongoExtension beforeTestExecution");
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null;
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.out.println("MongoExtension postProcessTestInstance");
    }
}