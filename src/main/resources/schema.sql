DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS restaurants;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE restaurants
(
    id    INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name  VARCHAR(255)      NOT NULL,
    votes INTEGER DEFAULT 0 NOT NULL
);

CREATE TABLE users
(
    id                  INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    email               VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    last_time_voted     TIMESTAMP,
    voted_restaurant_id INTEGER,
    FOREIGN KEY (voted_restaurant_id) REFERENCES RESTAURANTS (id)
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id            INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    restaurant_id INTEGER      NOT NULL,
    name          VARCHAR(255) NOT NULL,
    price         LONG         NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);