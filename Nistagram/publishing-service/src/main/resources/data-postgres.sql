 /* 
INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'pero12', true, true) on conflict (username) do nothing;
INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'ana0', true, false) on conflict (username) do nothing;
insert into location (id, name) VALUES (11, 'zvornik') on conflict (id) do nothing;
insert into location (id, name) VALUES (12, 'novi sad') on conflict (id) do nothing;
insert into post (id, profile_id, description, location_id) VALUES (nextval('post_seq'), 1, 'Post_example1', 11);
insert into post (id, profile_id, description) VALUES (nextval('post_seq'), 2, 'Post_example2');
insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'firstStory', true);
insert into story (id, owner_id, description, is_highlight, timestamp, location_id) VALUES (nextval('story_seq'), 2, 'secondStory', true, '2021-02-02 14:10:00', 11);
insert into story (id, owner_id, description, is_highlight) VALUES (nextval('story_seq'), 1, 'perin story', true);
insert into collection (id, name) VALUES (6, 'first_collection') on conflict (id) do nothing;
insert into collection (id, name) VALUES (5, 'my_collection') on conflict (id) do nothing;
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (12, 1, 13, 6);
insert into favourite_post (id, owner_id, post_id, collection_id) VALUES (11, 1, 22, 5);
insert into hashtag (id, name) VALUES (11, '#fashion') on conflict (id) do nothing;
insert into hashtag (id, name) VALUES (12, '#m') on conflict (id) do nothing;
insert into post_hashtags (hashtag_id, post_id) VALUES (12, 1) on conflict (hashtag_id, post_id) do nothing;
insert into post_hashtags (hashtag_id, post_id) VALUES (12, 2) on conflict (hashtag_id, post_id) do nothing;
insert into story_hashtags (hashtag_id, story_id) VALUES (11, 2) on conflict (hashtag_id, story_id) do nothing;
insert into story_tagged_profiles (profile_id, story_id) VALUES (1, 2) on conflict (profile_id, story_id) do nothing;
insert into story_tagged_profiles (profile_id, story_id) VALUES (2, 2) on conflict (profile_id, story_id) do nothing;
insert into post_hashtags (hashtag_id, post_id) VALUES (12, 2) on conflict (hashtag_id, post_id) do nothing;
*/
/* 
INSERT INTO profile (id, username, is_public, allowed_tagging) VALUES 
			(nextval('profile_seq'), 'nada', true, true) on conflict (username) do nothing;
			
INSERT INTO post(id, description, is_deleted, timestamp, location_id, profile_id) VALUES
			(1, 'post1', false, null, null, 1);
INSERT INTO post(id, description, is_deleted, timestamp, location_id, profile_id) VALUES
			(2, 'post2', false, null, null, 1);
INSERT INTO post(id, description, is_deleted, timestamp, location_id, profile_id) VALUES
			(3, 'post3', false, null, null, 1);
			
INSERT INTO story(id, description,for_close_friends,is_highlight, is_deleted, timestamp, location_id, owner_id) VALUES
			(1, 'story1', false, false,false, null, null, 1);
			
INSERT INTO report_content_request (id, content_id, content_type, is_approved, reason, profile_id) VALUES 
			(1, 1, 'post', false, 'Neprikladan post1', 1) on conflict (id) do nothing;
			
INSERT INTO report_content_request (id, content_id, content_type, is_approved, reason, profile_id) VALUES 
			(2, 2, 'post', false, 'Neprikladan post2', 1) on conflict (id) do nothing;
			
INSERT INTO report_content_request (id, content_id, content_type, is_approved, reason, profile_id) VALUES 
			(3, 3, 'post', false, 'Neprikladan post3', 1) on conflict (id) do nothing;
		
INSERT INTO report_content_request (id, content_id, content_type, is_approved, reason, profile_id) VALUES 
			(4, 1, 'story', false, 'Neprikladan story1', 1) on conflict (id) do nothing;
			
INSERT INTO report_content_request (id, content_id, content_type, is_approved, reason, profile_id) VALUES 
			(5, 1, 'post', false, 'Neprikladan post11', 1) on conflict (id) do nothing;*/