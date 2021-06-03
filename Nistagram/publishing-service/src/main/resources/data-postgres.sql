insert into profile (id, username, is_public, allowed_tagging) VALUES (1, 'user_1', true, true)  on conflict (username) do nothing;
insert into profile (id, username, is_public, allowed_tagging) VALUES (2, 'user_2', true, true) on conflict (username) do nothing;

insert into post (id, profile_id, description) VALUES (13, 1, 'bla bla') on conflict (id) do nothing;
insert into post (id, profile_id, description) VALUES (22, 2, 'prom') on conflict (id) do nothing;

insert into story (id, owner_id, description, is_highlight) VALUES (13, 1, '111', true) on conflict (id) do nothing;
insert into story (id, owner_id, description, is_highlight) VALUES (22, 1, '666', true) on conflict (id) do nothing;

insert into collection (id, name) VALUES (6, 'first_collection') on conflict (id) do nothing;
insert into collection (id, name) VALUES (5, 'my_collection') on conflict (id) do nothing;

insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (22, 2, 13, 6) on conflict (id) do nothing;
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (11, 2, 22, 5) on conflict (id) do nothing;

