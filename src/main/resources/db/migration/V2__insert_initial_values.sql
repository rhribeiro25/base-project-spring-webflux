INSERT INTO roles(name, description) VALUES
('SUPER', 'Super Usuário'),
('ADMIN', 'Administrador'),
('EMPLOYEE', 'Funcionário'),
('INTERN', 'Estagiário');

INSERT INTO companies(name)
VALUES ('Ifood');

INSERT INTO addresses(zipCode, street, num, complement, district, city, company_id)
VALUES ('37500-00', 'Rodovia Itajubá Piranguinho', '45', 'KM 5', 'Santa Barbara', 'Piranguinho',
(SELECT id FROM companies WHERE name = 'Ifood'));

INSERT INTO users(first_name, middle_name, last_name, email, password, created_at, updated_at, role_id)
VALUES ('Renan', 'Henrique', 'Ribeiro', 'rhribeiro_25@hotmail.com', '12345678', now(), now(),
(SELECT id FROM roles WHERE name = 'ADMIN'));

INSERT INTO phones(ddd, num, phone_type, user_id)
VALUES ('35', '9 9163-7941', 'CEL',
(SELECT id FROM users WHERE email = 'rhribeiro_25@hotmail.com'));

INSERT INTO users_companies(user_id, company_id) VALUES (
(SELECT id FROM users WHERE email = 'rhribeiro_25@hotmail.com'),
(SELECT id FROM companies WHERE name = 'Ifood'));