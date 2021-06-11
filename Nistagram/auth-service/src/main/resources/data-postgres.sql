/*Sifre su Hello1234#*/

INSERT INTO authority (id,name) VALUES (1,'ROLE_ADMIN');
INSERT INTO authority (id,name) VALUES (2,'ROLE_REGULAR');
INSERT INTO authority (id,name) VALUES (3,'ROLE_AGENT');

INSERT INTO permission (id,name) VALUES (2,'updateProfile');
INSERT INTO permission (id,name) VALUES (3,'improveProfileAsAgent');
INSERT INTO permission (id,name) VALUES (4,'findAllowTaggingProfile');
INSERT INTO permission (id,name) VALUES (5,'createFavouritePost');
INSERT INTO permission (id,name) VALUES (6,'getFavouritePosts');
INSERT INTO permission (id,name) VALUES (7,'createPost');
INSERT INTO permission (id,name) VALUES (8,'deletePost');
INSERT INTO permission (id,name) VALUES (9,'createStory');
INSERT INTO permission (id,name) VALUES (10,'getCollectionsByUsername');
INSERT INTO permission (id,name) VALUES (11,'createClick');
INSERT INTO permission (id,name) VALUES (12,'getClicks');
INSERT INTO permission (id,name) VALUES (13,'createComment');
INSERT INTO permission (id,name) VALUES (14,'createReaction');
INSERT INTO permission (id,name) VALUES (15,'createFriendship');
INSERT INTO permission (id,name) VALUES (16,'deleteFriendship');
INSERT INTO permission (id,name) VALUES (17,'mutedOrCloseFriendship');
INSERT INTO permission (id,name) VALUES (18,'postOrStoryNotification');
INSERT INTO permission (id,name) VALUES (19,'deleteProfile');
INSERT INTO permission (id,name) VALUES (20,'deleteFollowRequest');
INSERT INTO permission (id,name) VALUES (21,'getRequests');
INSERT INTO permission (id,name) VALUES (22,'uploadFile');
INSERT INTO permission (id,name) VALUES (23,'deleteFile');

INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,2);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,3);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,4);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,5);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,6);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,7);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,8);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,9);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,10);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,11);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (3,12);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,13);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,14);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,15);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,16);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,17);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,18);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,19);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,20);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,21);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,22);
INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (2,23);

