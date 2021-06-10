insert into roles(name, description)
values ('ADMIN', 'Administrador');

insert into users(first_name, middle_name, last_name, email, password, created_at, updated_at, role_id)
values ('Renan', 'Henrique', 'Ribeiro', 'rhribeiro_25@hotmail.com', '12345678', now(), now(), 1);