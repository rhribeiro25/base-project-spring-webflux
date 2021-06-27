CREATE TABLE IF NOT EXISTS users (
	id bigint GENERATED ALWAYS AS IDENTITY,
	first_name VARCHAR(63) NOT NULL,
	middle_name VARCHAR(63),
	last_name VARCHAR(63) NOT NULL,
	mother_name VARCHAR(255),
	email VARCHAR(255) NOT NULL UNIQUE,
	password VARCHAR(8) NOT NULL,
    phone VARCHAR(23)  NOT NULL,
    role VARCHAR(63)  NOT NULL,
    is_activated BOOLEAN  NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id));