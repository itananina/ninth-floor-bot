--CHARACTERS

CREATE TABLE indicators (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    score           INTEGER NOT NULL DEFAULT 0,
    is_locked        INTEGER NOT NULL DEFAULT 0,
    type            TEXT NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

CREATE TABLE states (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    strength_id     INTEGER NOT NULL,
    dexterity_id    INTEGER NOT NULL,
    iq_id           INTEGER NOT NULL,
    health_id       INTEGER NOT NULL,
    fatigue_id      INTEGER NOT NULL,
    charisma_id     INTEGER NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    FOREIGN KEY (strength_id) REFERENCES indicators(id),
    FOREIGN KEY (dexterity_id) REFERENCES indicators(id),
    FOREIGN KEY (iq_id) REFERENCES indicators(id),
    FOREIGN KEY (health_id) REFERENCES indicators(id),
    FOREIGN KEY (fatigue_id) REFERENCES indicators(id),
    FOREIGN KEY (charisma_id) REFERENCES indicators(id)
);

CREATE TABLE archetypes (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    state_id        INTEGER NOT NULL,
    name            TEXT NOT NULL,
    description     TEXT NOT NULL,
    play_frequency  INTEGER NOT NULL DEFAULT 0,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    FOREIGN KEY (state_id) REFERENCES states(id)
);

CREATE TABLE characters (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    state_id        INTEGER NOT NULL,
    name            TEXT,
    archetype_id    INTEGER NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    FOREIGN KEY (state_id) REFERENCES states(id),
    FOREIGN KEY (archetype_id) REFERENCES archetypes(id)
);

CREATE TABLE locations (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    description     TEXT NOT NULL,
    name            TEXT NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

CREATE TABLE npcs (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    state_id        INTEGER NOT NULL,
    archetype_id    INTEGER NOT NULL,
    greeting_phrase TEXT NOT NULL,
    location_id     INTEGER NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    FOREIGN KEY (state_id) REFERENCES states(id),
    FOREIGN KEY (archetype_id) REFERENCES archetypes(id),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);

--CHAT PLAYERS

CREATE TABLE players (
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    username      TEXT NOT NULL,
    first_name    TEXT NOT NULL,
    last_name     TEXT NOT NULL,
    character_id  INTEGER,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp,
    FOREIGN KEY (character_id) REFERENCES characters(id)
);

create table chats (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    pr_user     INTEGER NOT NULL,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

CREATE TABLE chats_players (
    chat_id     INTEGER NOT NULL,
    player_id   INTEGER NOT NULL,
    primary key (chat_id, player_id),
    FOREIGN KEY (chat_id) REFERENCES chats(id),
    FOREIGN KEY (player_id) REFERENCES players(id)
);

--MISSIONS

CREATE TABLE missions (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    chat_id         INTEGER NOT NULL,
    npc_id          INTEGER NOT NULL,
    main_player_id  INTEGER,
    status          TEXT NOT NULL,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp,
    FOREIGN KEY (main_player_id) REFERENCES players(id)
);
