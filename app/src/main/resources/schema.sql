DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

DROP TABLE IF EXISTS url_checks;

CREATE TABLE url_checks (
    id serial PRIMARY KEY,
    status_code INT NOT NULL,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description text,
    url_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL
);