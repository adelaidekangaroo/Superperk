package superperk.pipboy.repository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import superperk.hug.extensions.annotations.MongoInsert;
import superperk.hug.testcontainers.annotations.EnableMongoContainer;
import superperk.hug.testcontainers.annotations.EnablePostgresContainer;
import superperk.pipboy.model.postgres.Special;
import superperk.pipboy.repository.data.mongo.MongoSpecialDataTest;
import superperk.pipboy.repository.mongo.MongoSpecialRepository;
import superperk.pipboy.repository.postgres.PostgresSpecialRepository;

import java.util.List;

import static superperk.pipboy.repository.data.postgres.PostgresSpecialDataTest.*;

@SpringBootTest
@ActiveProfiles("test")
@EnablePostgresContainer(image = "postgres:14.3")
@EnableMongoContainer(image = "mongo:4.4.2")
@MongoInsert(location = "db/mongo/populate/specials.json", collection = "specials")
public class SpecialRepositoryTest {

   /* @SpringContainer
    @ContainerImage(image = "postgres:14.3")
    @ContainerReuse(byProfiles = {"test"})
    private PostgresContainer postgresContainer; // container starts automatically*/

    @Autowired
    private PostgresSpecialRepository postgresSpecialRepository;

    @Autowired
    private MongoSpecialRepository mongoSpecialRepository;

    @Rule // reset changes after each test
    public final Special PREPARED_FOR_CREATE_SPECIAL = Special.builder()
            .title("New special")
            .description("New special description")
            .build();

    @Test
    void mongo_should_return_all_specials() {
        var expected = List.of(
                MongoSpecialDataTest.STRENGTH,
                MongoSpecialDataTest.PERCEPTION,
                MongoSpecialDataTest.ENDURANCE,
                MongoSpecialDataTest.CHARISMA,
                MongoSpecialDataTest.INTELLIGENCE,
                MongoSpecialDataTest.AGILITY,
                MongoSpecialDataTest.LUCK
        );
        var actual = mongoSpecialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.contains(expected.toArray()));
    }

    @Test
    void postgres_should_return_all_specials() {
        var expected = List.of(STRENGTH, PERCEPTION, ENDURANCE, CHARISMA, INTELLIGENCE, AGILITY, LUCK);
        var actual = postgresSpecialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.contains(expected.toArray()));
    }

    @Test
    @Transactional
        // for roll back the changes made in tests
    void should_return_saved_special() {
        var expected = PREPARED_FOR_CREATE_SPECIAL;
        postgresSpecialRepository.save(expected);
        var actual = postgresSpecialRepository.findById(expected.getId()).orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
        // for roll back the changes made in tests
    void should_remove_special_by_id() {
        postgresSpecialRepository.deleteById(STRENGTH.getId());
        var actual = postgresSpecialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.not(Matchers.contains(STRENGTH)));
    }
}