-- Drop tables if they exist
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS lottery CASCADE;
DROP TABLE IF EXISTS user_ticket CASCADE;

CREATE SEQUENCE user_id_seq MINVALUE 1000000000;

-- CREATE TABLE users (
--      id SERIAL PRIMARY KEY,
--      name VARCHAR(255) NOT NULL UNIQUE,
--      password VARCHAR(255) NOT NULL,
--      role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'MEMBER'))
-- );


CREATE TABLE users (
     id BIGINT PRIMARY KEY DEFAULT nextval('user_id_seq'),
     name VARCHAR(255) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL,
     role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'MEMBER'))
);


CREATE TABLE lottery (
     id SERIAL PRIMARY KEY,
     ticket VARCHAR(6) NOT NULL CHECK (LENGTH(ticket) = 6),
     price INTEGER NOT NULL
);

CREATE TABLE user_ticket (
     id SERIAL PRIMARY KEY,
     user_id INTEGER REFERENCES users(id),
     ticket_id INTEGER REFERENCES lottery(id)
);

-- Initial data
INSERT INTO users(name, password, role) VALUES('admin', '$2a$10$DmA07bhtA6nITrzlHvx.jeyFx5NJ30EP5XZSqIVYlipLKkKnne4Ve', 'ADMIN');
INSERT INTO users(name, password, role) VALUES('member1', '$2a$10$DmA07bhtA6nITrzlHvx.jeyFx5NJ30EP5XZSqIVYlipLKkKnne4Ve', 'MEMBER');
INSERT INTO users(name, password, role) VALUES('member2', '$2a$10$DmA07bhtA6nITrzlHvx.jeyFx5NJ30EP5XZSqIVYlipLKkKnne4Ve', 'MEMBER');

INSERT INTO lottery(ticket, price) VALUES('234568', 80);
INSERT INTO lottery(ticket, price) VALUES('234568', 80);
INSERT INTO lottery(ticket, price) VALUES('443467', 80);
INSERT INTO lottery(ticket, price) VALUES('398746', 120);
INSERT INTO lottery(ticket, price) VALUES('398746', 120);
INSERT INTO lottery(ticket, price) VALUES('987309', 80);

INSERT INTO user_ticket(user_id, ticket_id) VALUES(1000000002, 3);
INSERT INTO user_ticket(user_id, ticket_id) VALUES(1000000002, 6);
