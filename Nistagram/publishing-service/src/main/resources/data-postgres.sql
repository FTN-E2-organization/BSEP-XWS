INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'pero123', true, true);
INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'ana00', true, false);

insert into post (id, profile_id, description) VALUES (13, 1, 'bla bla');
insert into post (id, profile_id, description) VALUES (22, 2, 'prom');

insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'firstStory', true);
insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'secondStory', true);

insert into collection (id, name) VALUES (6, 'first_collection');
insert into collection (id, name) VALUES (5, 'my_collection');

insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (22, 2, 13, 6);
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (11, 2, 22, 5);
