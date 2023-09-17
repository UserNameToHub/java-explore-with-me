create table if not exists users
(
    id    INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR NOT NULL,
    name  VARCHAR NOT NULL
);

create table if not exists categories
(
    id   INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

create table if not exists locations
(
    id  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT
);

create table if not exists events
(
    id                INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation        VARCHAR                     NOT NULL,
    category_id       INTEGER REFERENCES categories (id),
    created_on        TIMESTAMP WITHOUT TIME ZONE,
    description       VARCHAR,
    event_date        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id           INTEGER                     NOT NULL REFERENCES users (id),
    location_id       INTEGER                     NOT NULL REFERENCES locations (id),
    paid              BOOLEAN                     NOT NULL,
    participant_limit INTEGER,
    published_on      TIMESTAMP WITHOUT TIME ZONE,
    state             VARCHAR,
    title             VARCHAR                     NOT NULL
)

