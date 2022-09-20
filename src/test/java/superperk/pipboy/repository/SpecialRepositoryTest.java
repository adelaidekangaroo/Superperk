package superperk.pipboy.repository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import superperk.pipboy.model.Special;

import java.util.List;

import static superperk.pipboy.repository.SpecialDataTest.*;

class SpecialRepositoryTest extends AbstractContainerTest {

    @Autowired
    private SpecialRepository specialRepository;

    @Rule // reset changes after each test
    private final Special PREPARED_FOR_CREATE_SPECIAL = Special.builder()
            .title("New special")
            .description("New special description")
            .build();

    @Test
    void should_return_all_specials() {
        var expected = List.of(STRENGTH, PERCEPTION, ENDURANCE, CHARISMA, INTELLIGENCE, AGILITY, LUCK);
        var actual = specialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.contains(expected.toArray()));
    }

    @Test
    @Transactional /* for roll back the changes made in tests */
    void should_return_saved_special() {
        var expected = PREPARED_FOR_CREATE_SPECIAL;
        specialRepository.save(expected);
        var actual = specialRepository.findById(expected.getId()).orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Transactional /* for roll back the changes made in tests */
    void should_remove_special_by_id() {
        specialRepository.deleteById(STRENGTH.getId());
        var actual = specialRepository.findAll();
        MatcherAssert.assertThat(actual, Matchers.not(Matchers.contains(STRENGTH)));
    }
}