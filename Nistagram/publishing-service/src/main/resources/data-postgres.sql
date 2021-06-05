INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'pero123', true, true);
INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'ana00', true, false);

insert into location (id, name) VALUES (11, 'zvornik');
insert into location (id, name) VALUES (12, 'novi sad');

insert into post (id, profile_id, description, location_id) VALUES (nextval('post_seq'), 1, 'Post_example1', 11);
insert into post (id, profile_id, description) VALUES (nextval('post_seq'), 2, 'Post_example2');

insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'firstStory', true);
insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'secondStory', true);

insert into collection (id, name) VALUES (6, 'first_collection');
insert into collection (id, name) VALUES (5, 'my_collection');

/*insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (12, 1, 13, 6);
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (11, 1, 22, 5);*/


insert into hashtag (id, name) VALUES (11, '#fashion');
insert into hashtag (id, name) VALUES (12, '#m');