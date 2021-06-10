CREATE TABLE IF NOT EXISTS roles (
	id bigint GENERATED ALWAYS AS IDENTITY,
	name varchar(127) NOT NULL,
	description varchar(255) NOT NULL,
	CONSTRAINT roles_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
	id bigint GENERATED ALWAYS AS IDENTITY,
	first_name varchar(63) NOT NULL,
	middle_name varchar(63),
	last_name varchar(63) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(8) NOT NULL,
	created_at varchar(63) NOT NULL,
	updated_at varchar(63) NOT NULL,
    role_id bigint NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT roles_fk FOREIGN KEY (id) REFERENCES roles(id)
);