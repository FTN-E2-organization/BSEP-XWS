/*Sifre su Hello1234#*/

INSERT INTO authority (id,name) VALUES (1,'ROLE_ADMIN') on conflict (id) do nothing;
INSERT INTO authority (id,name) VALUES (2,'ROLE_REGULAR') on conflict (id) do nothing;
INSERT INTO authority (id,name) VALUES (3,'ROLE_AGENT') on conflict (id) do nothing;

INSERT INTO permission (id,name) VALUES (1,'reportContent') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (2,'updateProfile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (3,'improveProfileAsAgent') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (4,'findAllowTaggingProfile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (5,'createFavouritePost') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (6,'getFavouritePosts') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (7,'createPost') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (8,'deletePost') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (9,'createStory') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (10,'getCollectionsByUsername') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (11,'createClick') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (12,'getClicks') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (13,'createComment') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (14,'createReaction') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (15,'createFriendship') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (16,'deleteFriendship') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (17,'mutedOrCloseFriendship') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (18,'postOrStoryNotification') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (19,'deleteProfile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (20,'deleteFollowRequest') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (21,'getRequests') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (22,'uploadFile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (23,'deleteFile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (24,'getReactions') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (25,'blockOrUnblockProfiles') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (26,'getContentRequests') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (27,'removeContent') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (28,'blockProfile') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (29,'createVerificationRequest') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (30,'createAgentRegistrationRequest') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (31,'getAllAgentRequestsByUsername') on conflict (id) do nothing;
INSERT INTO permission (id,name) VALUES (32,'manageAgentRegistrationRequest') on conflict (id) do nothing;

INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,1) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,2) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,3) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,4) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,5) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,6) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,7) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,8) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,9) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,10) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,11) on conflict (authority_id,permission_id)do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (3,12) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,13) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,14) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,15) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,16) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,17) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,18) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,19) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,20) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,21) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,22) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,23) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,24) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,25) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,26) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,27) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,28) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,29) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,30) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,31) on conflict (authority_id,permission_id) do nothing;
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,32) on conflict (authority_id,permission_id) do nothing;

/*
INSERT INTO admin (id, username, email, password, salt, is_blocked) VALUES 
			(nextval('users_seq'), 'admin', 'isa.user.test@gmail.com', '$2a$12$hUyaJlrEqZzHCl19qkHKUOA/C9Z4.fLIuft46tmGqkbM3NH6w6nPa','cb117ff4',false) on conflict (id) do nothing;

INSERT INTO users_authorities(user_id, authority_id) VALUES (1,1) on conflict (user_id, authority_id) do nothing;*/
