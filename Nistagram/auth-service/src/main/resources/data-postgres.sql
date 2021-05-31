/*Sifre su Hello1234#*/

INSERT INTO role (id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO role (id,name) VALUES (2,'ROLE_REGULAR');
INSERT INTO role (id,name) VALUES (3,'ROLE_AGENT');


INSERT INTO admin (id, username, email,  password) VALUES 
			(nextval('users_seq'),'admin', 'admin@mail.com','$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC');

INSERT INTO profile (id, username, email, password, name, date_of_birth, gender, is_public) VALUES 
			(nextval('users_seq'), 'pero123', 'pero123@gmail.com', '$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC', 
			'Pero Peric', '1988-02-25', 0, true);
INSERT INTO profile (id, username, email, password, name, date_of_birth, gender, is_public) VALUES 
			(nextval('users_seq'), 'ana00', 'ana00@gmail.com', '$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC', 
			'Ana Anic', '2000-12-01', 1, true);
			
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 3);
INSERT INTO users_roles (user_id, role_id) VALUES (3, 2);