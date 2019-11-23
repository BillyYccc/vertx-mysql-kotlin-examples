USE test;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INTEGER,
    name VARCHAR(25),
    email VARCHAR(50),
    registered_date DATE
);