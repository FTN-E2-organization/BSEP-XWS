/*Sifre su Hello1234#*/

INSERT INTO authority (id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO authority (id,name) VALUES (2,'ROLE_REGULAR');
INSERT INTO authority (id,name) VALUES (3,'ROLE_AGENT');


INSERT INTO admin (id, username, email,  password) VALUES 
			(nextval('users_seq'),'admin', 'admin@mail.com','$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC') on conflict (username) do nothing;

INSERT INTO profile (id, username, email, password, name, date_of_birth, gender, is_public) VALUES 
			(nextval('users_seq'), 'pero123', 'pero123@gmail.com', '$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC', 
			'Pero Peric', '1988-02-25', 0, true) on conflict (username) do nothing;
INSERT INTO profile (id, username, email, password, name, date_of_birth, gender, is_public) VALUES 
			(nextval('users_seq'), 'ana00', 'ana00@gmail.com', '$2y$12$esSysQuIMJjPmQAxDLNtfuGrTuVn1xBO1ieY.tqMSo46SdUQyMUxC', 
			'Ana Anic', '2000-12-01', 1, true) on conflict (username) do nothing;
			
INSERT INTO users_authorities (user_id, authority_id) VALUES (1, 1);
INSERT INTO users_authorities (user_id, authority_id) VALUES (2, 2);
INSERT INTO users_authorities (user_id, authority_id) VALUES (2, 3);
INSERT INTO users_authorities (user_id, authority_id) VALUES (3, 2);