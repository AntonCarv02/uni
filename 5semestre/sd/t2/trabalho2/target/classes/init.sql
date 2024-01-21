CREATE TABLE users (
    username VARCHAR(255),
    email VARCHAR(255) unIQUE ,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(20), 
    PRIMARY KEY(username)
);

CREATE TABLE artists (
    id SERIAL,
    name VARCHAR(255) unique,
    type VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    performing BOOLEAN,
    approved BOOLEAN,
    PRIMARY KEY(id)
);

CREATE TABLE donatives (
    id SERIAL PRIMARY KEY,
    artistid INTEGER,
    donation_date DATE,
    username VARCHAR(255) REFERENCES users(username), 
    value INTEGER
);

CREATE TABLE performances (
    actuation_id SERIAL,
    artist_id INTEGER REFERENCES artists(id),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    actuation_date DATE,
    PRIMARY KEY(actuation_id,longitude,latitude,actuation_date)
);

CREATE TABLE rating(
    rating_id serial primary key,
    artist_id INTEGER REFERENCES artists(id),
    username VARCHAR(255) REFERENCES users(username), 
    rating INTEGER 
);

drop table artists;
drop table users;
drop table performances;
drop table donatives;
drop table rating;


INSERT INTO artists (name, type, latitude, longitude, performing, approved)
VALUES ('John Doe', 'Musician', 40.7128, -74.0060, true, true);

INSERT INTO artists (name, type, latitude, longitude, performing, approved)
VALUES ('Jane Smith', 'Painter', 34.0522, -118.2437, false, false);

INSERT INTO rating (artist_id, username, rating)
VALUES (1, 'a', 4);

INSERT INTO rating (artist_id, username, rating)
VALUES (1, 'a', 3);


INSERT INTO performances (artist_id, latitude, longitude, actuation_date) VALUES (1, 40.7128, -74.0060, '2022-01-01');
INSERT INTO performances (artist_id, latitude, longitude, actuation_date) VALUES (2, 34.0522, -118.2437, '2024-02-15');

INSERT INTO performances (artist_id, latitude, longitude, actuation_date) VALUES (2, 40.7128, -74.0060, '2022-01-01');
INSERT INTO performances (artist_id, latitude, longitude, actuation_date) VALUES (1, 34.0522, -118.2437, '2024-02-15');
