INSERT INTO special (TITLE, DESCRIPTION)
VALUES ('Strength',
        'Brute physical strength. A high strength score is good for brutal characters.'),
       ('Perception',
        'Perception is a measurement of your environmental and situational awareness.'),
       ('Endurance',
        'Stamina and physical toughness. A character with a high Endurance will survive where others may not.'),
       ('Charisma',
        'A combination of appearance and charm. A high Charisma is important for characters that want to influence people with words.'),
       ('Intelligence',
        'Knowledge, wisdom and the ability to think quickly. A high Intelligence is important for any character.'),
       ('Agility',
        'Coordination and the ability to move well. A high Agility is important for any active character.'),
       ('Luck',
        'Fate. Karma. An extremely high or low Luck will affect the character - somehow. Events and situations will be changed by how lucky (or unlucky) your character is.');

INSERT INTO level (number)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);

INSERT INTO perk (title, description, effects, required_level_id)
VALUES ('Scrounger',
        'With the Scrounger perk, you wll find considerably more ammunition in containers than you normally would.',
        'Find more ammunition in containers',
        1),
       ('Finesse',
        'With the Finesse perk, you have a higher chance to score a critical hit on an opponent in combat, equivalent to 5 extra points of Luck.',
        'Higher critical hit chance (equivalent to +5 Luck)',
        10),
       ('Daddy''s Boy',
        'Just like dear old Dad, you’ve devoted your time to intellectual pursuits. You gain an additional 5 points to both the Science and Medicine skills.',
        '+5 Science, +5 Medicine',
        2);

INSERT INTO perk_unlock_requirements (perk_id, special_id, requirement_special_level)
VALUES (3, 5, 4);