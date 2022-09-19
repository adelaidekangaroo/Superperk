DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE special
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    title       VARCHAR(12)  NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE level
(
    id     SERIAL NOT NULL PRIMARY KEY,
    number INT    NOT NULL
);

CREATE TABLE perk
(
    id                SERIAL       NOT NULL PRIMARY KEY,
    title             VARCHAR(12)  NOT NULL,
    description       VARCHAR(255) NOT NULL,
    effects           VARCHAR(255) NOT NULL,
    required_level_id INT          NOT NULL,
    FOREIGN KEY (required_level_id) REFERENCES level (id)
);

CREATE TABLE perk_unlock_requirements
(
    perk_id                   INT      NOT NULL,
    special_id                INT      NOT NULL,
    requirement_special_level SMALLINT NOT NULL,
    CONSTRAINT perk_special_requirement_ids UNIQUE (perk_id, special_id),
    FOREIGN KEY (perk_id) REFERENCES perk (id),
    FOREIGN KEY (special_id) REFERENCES special (id)
);