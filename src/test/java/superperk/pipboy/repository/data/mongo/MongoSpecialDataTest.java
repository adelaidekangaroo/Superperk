package superperk.pipboy.repository.data.mongo;

import superperk.pipboy.model.mongo.Special;

public final class MongoSpecialDataTest {

    public final static Special STRENGTH = Special.builder()
            .title("Strength")
            .description("Brute physical strength. A high strength score is good for brutal characters.")
            .build();
    public final static Special PERCEPTION = Special.builder()
            .title("Perception")
            .description("Perception is a measurement of your environmental and situational awareness.")
            .build();
    public final static Special ENDURANCE = Special.builder()
            .title("Endurance")
            .description("Stamina and physical toughness. A character with a high Endurance will survive where others may not.")
            .build();
    public final static Special CHARISMA = Special.builder()
            .title("Charisma")
            .description("A combination of appearance and charm. A high Charisma is important for characters that want to influence people with words.")
            .build();
    public final static Special INTELLIGENCE = Special.builder()
            .title("Intelligence")
            .description("Knowledge, wisdom and the ability to think quickly. A high Intelligence is important for any character.")
            .build();
    public final static Special AGILITY = Special.builder()
            .title("Agility")
            .description("Coordination and the ability to move well. A high Agility is important for any active character.")
            .build();
    public final static Special LUCK = Special.builder()
            .title("Luck")
            .description("Fate. Karma. An extremely high or low Luck will affect the character - somehow. Events and situations will be changed by how lucky (or unlucky) your character is.")
            .build();

    private MongoSpecialDataTest() {
    }

}