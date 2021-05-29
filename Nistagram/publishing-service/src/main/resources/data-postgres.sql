insert into profile (id, username, is_public, allowed_tagging) 
	VALUES (1, 'user_1', true, true);

insert into post (id, profile_id, description) VALUES (1, 1, 'bla bla');
insert into post (id, profile_id, description) VALUES (2, 1, 'prom');