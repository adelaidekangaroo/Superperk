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
import superperk.pipboy.model.Special;
import superperk.pipboy.testcontainers.v2.PostgresContainer;
import superperk.pipboy.testcontainers.v2.SpringContainer;
import superperk.pipboy.testcontainers.v2.SpringContainerImage;
import superperk.pipboy.testcontainers.v2.SpringContainerReuse;

import java.util.List;

import static superperk.pipboy.repository.SpecialDataTest.*;

@SpringBootTest
@ActiveProfiles("test")
public class SpecialRepositoryTest {

    @SpringContainer
    @SpringContainerImage(image = "postgres:14.3") // optional
    @SpringContainerReuse(byProfiles = {"test"}) // optional
    private PostgresContainer<?> postgresContainer; // container starts automatically

    @Autowired
    private SpecialRepository specialRepository;

    @Rule // reset changes after each test
    public final Special PREPARED_FOR_CREATE_SPECIAL = Special.builder()
            .title("New special")
            .description("New special description")
            .build();

    @Test
    void should_return_all_specials() throws InterruptedException {
        var expected = List.of(STRENGTH, PERCEPTION, ENDURANCE, CHARISMA, INTELLIGENCE, AGILITY, LUCK);
        var actual = specialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.contains(expected.toArray()));
        Thread.sleep(1000);

    }

    @Test
    @Transactional
        // for roll back the changes made in tests
    void should_return_saved_special() {
        var expected = PREPARED_FOR_CREATE_SPECIAL;
        specialRepository.save(expected);
        var actual = specialRepository.findById(expected.getId()).orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional
        // for roll back the changes made in tests
    void should_remove_special_by_id() {
        specialRepository.deleteById(STRENGTH.getId());
        var actual = specialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.not(Matchers.contains(STRENGTH)));
    }
}