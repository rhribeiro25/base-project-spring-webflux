insert into roles(name, description)
values ('SUPER', 'Super Usuário'),
('ADMIN', 'Administrador'),
('EMPLOYEE', 'Funcionário'),
('INTERN', 'Estagiário');

insert into companies(name)
values ('Ifood');

insert into addresses(zipCode, street, num, complement, district, city, company_id)
values ('37500-00', 'Rodovia Itajubá Piranguinho', '45', 'KM 5', 'Santa Barbara', 'Piranguinho', 1);

insert into users(first_name, middle_name, last_name, email, password, created_at, updated_at, role_id)
values ('Renan', 'Henrique', 'Ribeiro', 'rhribeiro_25@hotmail.com', '12345678', now(), now(), 1);

insert into phones(ddd, num, phone_type, user_id)
values ('35', '9 9163-7941', 'CEL', 1);

insert into users_companies(user_id, company_id)
values (1, 1);