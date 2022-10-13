CREATE TABLE IF NOT EXISTS users
(
    id    INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(255)                             NOT NULL,
    email VARCHAR(512)                             NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS item_requests
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    requestor_id INTEGER references users (id),
    description  VARCHAR(4000)                            NOT NULL,
    date_create  TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    CONSTRAINT pk_item_request PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS items
(
    id          INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(4000),
    available   BOOLEAN,
    owner_id    INTEGER references users (id),
    CONSTRAINT pk_item PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS bookings
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date_time TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    end_date_time   TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    item_id         INTEGER references items (id),
    booker_id       INTEGER references users (id),
    status          VARCHAR,
    CONSTRAINT pk_booking PRIMARY KEY (id)
    );


CREATE TABLE IF NOT EXISTS comments
(
    id        INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text      VARCHAR(4000)                            NOT NULL,
    item_id   INTEGER references items (id),
    author_id INTEGER references users (id),
    created   TIMESTAMP WITHOUT TIME ZONE              NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id)
    );

