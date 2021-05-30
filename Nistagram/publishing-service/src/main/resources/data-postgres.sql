insert into profile (id, username, is_public, allowed_tagging) VALUES (1, 'user_1', true, true);
insert into profile (id, username, is_public, allowed_tagging) VALUES (2, 'user_2', true, true);

insert into post (id, profile_id, description, is_deleted) VALUES (13, 1, 'bla bla', true);
insert into post (id, profile_id, description) VALUES (22, 1, 'prom');

insert into favourite_post (id, owner_id, post_id) VALUES (22, 2, 13);
insert into favourite_post (id, owner_id, post_id) VALUES (11, 2, 22);

insert into collection (id, name) VALUES (6, 'first_collection');
insert into collection (id, name) VALUES (5, 'my_collection');

/*
insert into favourite_post_collections (collections_id, favourite_posts_id) VALUES (5, 11);
insert into favourite_post_collections (collections_id, favourite_posts_id) VALUES (6, 22);
*/