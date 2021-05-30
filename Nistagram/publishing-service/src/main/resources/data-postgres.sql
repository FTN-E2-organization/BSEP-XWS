insert into profile (id, username, is_public, allowed_tagging) VALUES (1, 'user_1', true, true);
insert into profile (id, username, is_public, allowed_tagging) VALUES (2, 'user_2', true, true);

insert into post (id, profile_id, description) VALUES (1, 1, 'bla bla');
insert into post (id, profile_id, description) VALUES (2, 1, 'prom');

insert into favourite_post (id, owner_id, post_id) VALUES (1, 2, 1);
insert into favourite_post (id, owner_id, post_id) VALUES (2, 2, 2);

insert into collection (id, name) VALUES (1, 'first_collection');
insert into collection (id, name) VALUES (2, 'my_collection');

insert into favourite_post_collections (collections_id, favourite_posts_id) VALUES (1, 1);
insert into favourite_post_collections (collections_id, favourite_posts_id) VALUES (2, 2);