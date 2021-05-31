insert into profile (id, username, is_public, allowed_tagging) VALUES (1, 'user_1', true, true);
insert into profile (id, username, is_public, allowed_tagging) VALUES (2, 'user_2', true, true);

insert into post (id, profile_id, description) VALUES (13, 1, 'bla bla');
insert into post (id, profile_id, description) VALUES (22, 1, 'prom');

insert into collection (id, name) VALUES (6, 'first_collection');
insert into collection (id, name) VALUES (5, 'my_collection');

insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (22, 2, 13, 6);
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (11, 2, 22, 5);

