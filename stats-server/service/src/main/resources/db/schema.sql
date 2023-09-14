create table if not exists endpoint_hit
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    app       VARCHAR NOT NULL,
    uri       VARCHAR NOT NULL,
    ip        VARCHAR NOT NULL,
    timestamp TIMESTAMP without time zone
);