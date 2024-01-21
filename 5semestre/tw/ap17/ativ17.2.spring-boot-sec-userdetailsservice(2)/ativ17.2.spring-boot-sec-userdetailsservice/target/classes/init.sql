DROP TABLE IF EXISTS my_user_role;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS events;

CREATE TABLE app_user (
  user_name varchar(30) NOT NULL,
  user_pass varchar(255) NOT NULL,
  user_email varchar(255) NOT NULL,
  enable smallint NOT NULL DEFAULT 1,
  PRIMARY KEY (user_name)
);

INSERT INTO app_user (user_name, user_pass, user_email, enable)
VALUES ('admin', '$2a$10$dl8TemMlPH7Z/mpBurCX8O4lu0FoWbXnhsHTYXVsmgXyzagn..8rK', 'admin@gmail.com', 1);

-- user2 / teste123
INSERT INTO app_user (user_name, user_pass, user_email, enable)
VALUES ('user2', '$2a$10$bKWhb9hIUD3xxxtzfhvodugWIK3Gbw4vRySYOnBqy2O4gtqZ78jUK', 'user@gmail.com', 1);

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
    Event_date DATE NOT NULL
);
INSERT INTO Events (Event_name, Event_date) VALUES('Test Event 1', '2023-01-01');
INSERT INTO Events (Event_name, Event_date) VALUES('Test Event 2', '2023-02-15');

