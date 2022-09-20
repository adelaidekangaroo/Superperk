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

class SpecialRepositoryTest extends AbstractContainerTest {

    @Autowired
    private SpecialRepository specialRepository;

    private final static Special STRENGTH = Special.builder()
            .id(1L)
            .title("Strength")
            .description("Brute physical strength. A high strength score is good for brutal characters.")
            .build();
    private final static Special PERCEPTION = Special.builder()
            .id(2L)
            .title("Perception")
            .description("Perception is a measurement of your environmental and situational awareness.")
            .build();
    private final static Special ENDURANCE = Special.builder()
            .id(3L)
            .title("Endurance")
            .description("Stamina and physical toughness. A character with a high Endurance will survive where others may not.")
            .build();
    private final static Special CHARISMA = Special.builder()
            .id(4L)
            .title("Charisma")
            .description("A combination of appearance and charm. A high Charisma is important for characters that want to influence people with words.")
            .build();
    private final static Special INTELLIGENCE = Special.builder()
            .id(5L)
            .title("Intelligence")
            .description("Knowledge, wisdom and the ability to think quickly. A high Intelligence is important for any character.")
            .build();
    private final static Special AGILITY = Special.builder()
            .id(6L)
            .title("Agility")
            .description("Coordination and the ability to move well. A high Agility is important for any active character.")
            .build();
    private final static Special LUCK = Special.builder()
            .id(7L)
            .title("Luck")
            .description("Fate. Karma. An extremely high or low Luck will affect the character - somehow. Events and situations will be changed by how lucky (or unlucky) your character is.")
            .build();
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
    @Transactional
        // for roll back the changes made in tests
    void should_return_saved_special() {
        var expected = PREPARED_FOR_CREATE_SPECIAL;
        specialRepository.save(expected);
        var actual = specialRepository.findById(expected.getId())
                .orElseThrow();
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