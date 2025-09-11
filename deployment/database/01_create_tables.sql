CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles (
	id SMALLSERIAL NOT NULL UNIQUE,
	name VARCHAR(128) NOT NULL UNIQUE,
	description VARCHAR(255),
	PRIMARY KEY("id")
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
        CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    dni_number VARCHAR(32) NOT NULL UNIQUE,
    born_date DATE NULL,
    phone VARCHAR(16) NOT NULL,
    rol_id INTEGER NOT NULL,
    base_salary NUMERIC(10,2) NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT fk_rol
    FOREIGN KEY (rol_id)
    REFERENCES roles(id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;