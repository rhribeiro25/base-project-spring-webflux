CREATE TABLE IF NOT EXISTS roles (
	id bigint GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(127) NOT NULL,
	description VARCHAR(255) NOT NULL,
	CONSTRAINT role_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS companies (
	id bigint GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT company_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS addresses (
	id bigint GENERATED ALWAYS AS IDENTITY,
	zipCode VARCHAR(9) NOT NULL,
	street VARCHAR(255) NOT NULL,
	num VARCHAR(10) NOT NULL,
	complement VARCHAR(255) NOT NULL,
	district VARCHAR(127) NOT NULL,
	city VARCHAR(127) NOT NULL,
	company_id bigint,
	CONSTRAINT address_pk PRIMARY KEY (id),
	CONSTRAINT company_fk FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS users (
	id bigint GENERATED ALWAYS AS IDENTITY,
	first_name VARCHAR(63) NOT NULL,
	middle_name VARCHAR(63),
	last_name VARCHAR(63) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(8) NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
    role_id bigint NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS phones (
    id bigint GENERATED ALWAYS AS IDENTITY,
    ddd VARCHAR(2) NOT NULL,
    num VARCHAR(11) NOT NULL,
    phone_type VARCHAR(63) NOT NULL,
    user_id bigint,
    company_id bigint,
    CONSTRAINT phone_pk PRIMARY KEY (id),
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT company_fk FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS users_companies (
    user_id bigint NOT NULL,
    company_id bigint NOT NULL,
    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT company_fk FOREIGN KEY (company_id) REFERENCES companies(id)
);