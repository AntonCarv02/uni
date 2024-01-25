DROP TABLE IF EXISTS my_user_role;
DROP TABLE IF EXISTS ParticipantTimestamps;
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS Events;



CREATE TABLE app_user (
  user_name varchar(30) NOT NULL,
  user_pass varchar(255) NOT NULL,
  user_email varchar(255) NOT NULL,
  PRIMARY KEY (user_name)
);
-- admin / admin
INSERT INTO app_user (user_name, user_pass, user_email)
VALUES ('admin', '$2a$10$ut4IaKlVz/jJPY0eV0luFe5ICsgFDBrUaRN41QgJfTzLrqPdxt2lK', 'admin@gmail.com');

-- user2 / user
INSERT INTO app_user (user_name, user_pass, user_email)
VALUES ('user2', '$2a$10$beYW4jOdXlsJipQGMzKyp.uRRuam3WEjk3AI/i6TtXQEho/ypZagi', 'user@gmail.com');

CREATE TABLE my_user_role (
  user_name varchar(30) NOT NULL,
  user_role varchar(15) NOT NULL,
  FOREIGN KEY (user_name) REFERENCES app_user (user_name)
);

INSERT INTO my_user_role (user_name, user_role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO my_user_role (user_name, user_role) VALUES ('user2', 'ROLE_USER');  -- Corrected ROLE name

CREATE TABLE Events (
    Event_ID SERIAL PRIMARY KEY,
    Event_name VARCHAR(30) NOT NULL,
    Event_date DATE NOT NULL,
    Event_price FLOAT NOT NULL,
    Event_description VARCHAR(255) NOT NULL
);
INSERT INTO Events (Event_name, Event_date, Event_price, Event_description) VALUES('Test Event 1', '2023-01-01', 10, 'Description');
INSERT INTO Events (Event_name, Event_date, Event_price, Event_description) VALUES('Test Event 2', '2023-02-15', 20, 'Description');
INSERT INTO Events (Event_name, Event_date, Event_price, Event_description) VALUES('Test Event 3', '2024-02-01', 10, 'Description');
INSERT INTO Events (Event_name, Event_date, Event_price, Event_description) VALUES('Test Event 4', '2024-01-09', 20, 'Description');

CREATE TABLE participants (
    Event_Dorsal INT NOT NULL,
    Event_ID INT NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    Part_name VARCHAR(255) NOT NULL,
    NIF INT NOT NULL ,
    Part_gender VARCHAR(10) NOT NULL,
    Part_escalao VARCHAR(50) NOT NULL,
    Pago BOOLEAN NOT NULL,
    start TIMESTAMP,
    P1 TIMESTAMP,
    P2 TIMESTAMP,
    P3 TIMESTAMP,
    finish TIMESTAMP,
    Ttime BIGINT,
    entity int ,
    ref int ,
    PRIMARY KEY (NIF, Event_ID),
    FOREIGN KEY (user_name) REFERENCES app_user(user_name),
    FOREIGN KEY (Event_ID) REFERENCES Events(Event_ID)
);


INSERT INTO participants (Event_Dorsal, Event_ID, user_name, Part_name, NIF,Part_gender, Part_escalao, Pago)
VALUES
((SELECT COUNT(*) AS n FROM participants where Event_ID =1)+1,1, 'user2', 'Participant 1','123', 'M', 'Vet45', false),
((SELECT COUNT(*) as m FROM participants where Event_ID =2)+1, 2, 'user2', 'Participant 3','124',  'M', 'Vet45', false);


INSERT INTO participants (Event_Dorsal,Event_ID, user_name, Part_name,NIF, Part_gender, Part_escalao, Pago, start, P1, P2, P3, finish)
VALUES
((SELECT COUNT(*) AS n FROM participants where Event_ID =1)+1,1, 'user2', 'Participant 2','125', 'F', 'Vet30', true, '2024-01-01 12:34:56', '2024-01-01 12:40:56', '2024-01-01 12:54:56', '2024-01-01 13:04:56', '2024-01-01 13:24:56'),
((SELECT COUNT(*) as m FROM participants where Event_ID =2)+1,2, 'user2', 'Participant 4','126', 'F', 'Vet30', false, '2024-01-01 12:34:56', '2024-01-01 12:40:56', '2024-01-01 12:54:56', '2024-01-01 13:04:56', '2024-01-01 13:24:56');


