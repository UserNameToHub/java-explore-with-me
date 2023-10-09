create table if not exists users
(
    id    INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR NOT NULL,
    name  VARCHAR NOT NULL UNIQUE
);

create table if not exists categories
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

create table if not exists locations
(
    id  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL UNIQUE,
    lon FLOAT NOT NULL UNIQUE
);

create table if not exists compilations
(
    id     INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN DEFAULT FALSE,
    title  VARCHAR NOT NULL
);

create table if not exists events
(
    id                 INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation         VARCHAR                     NOT NULL,
    category_id        INTEGER REFERENCES categories (id),
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR,
    event_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id            INTEGER                     NOT NULL REFERENCES users (id),
    location_Id        INTEGER                     NOT NULL REFERENCES locations (id),
    paid               BOOLEAN DEFAULT FALSE,
    participant_limit  INTEGER DEFAULT 10,
    request_moderation BOOLEAN DEFAULT TRUE,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    state              VARCHAR,
    title              VARCHAR                     NOT NULL,
    compilation_id     INTEGER
);

create table if not exists requests
(
    id       INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created  TIMESTAMP WITHOUT TIME ZONE,
    event_id INTEGER NOT NULL REFERENCES events (id),
    user_id  INTEGER NOT NULL REFERENCES users (id),
    state    VARCHAR
);

create table if not exists compilation_events
(
    compilation_id INTEGER REFERENCES compilations (id) NOT NULL,
    event_id       INTEGER REFERENCES events (id)       NOT NULL,
    PRIMARY KEY (compilation_id, event_id)
);