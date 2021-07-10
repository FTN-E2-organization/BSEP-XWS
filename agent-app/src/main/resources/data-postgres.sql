/* Sifre su Hello.1234 */

INSERT INTO authority (id,name) VALUES (1,'ROLE_AGENT') on conflict (id) do nothing;
INSERT INTO authority (id,name) VALUES (2,'ROLE_CUSTOMER') on conflict (id) do nothing;

INSERT INTO permission (id,name) VALUES (1,'campaignManagement') on conflict (id) do nothing;

INSERT INTO authorities_permissions (authority_id,permission_id) VALUES (1,1) on conflict (authority_id,permission_id) do nothing;

INSERT INTO agent (id, username, email, password, authority_id) VALUES 
			(nextval('users_seq'), 'agent', 'agent@gmail.com', '$2a$12$SJ9EktANH.ddoAppSwIkYuoTA/xMXy01ZwsTkeQ3SGhNy/DtNIom2',1) on conflict (username) do nothing;
INSERT INTO customer (id, username, email, password, authority_id) VALUES 
			(nextval('users_seq'), 'customer', 'customer@gmail.com', '$2a$12$SJ9EktANH.ddoAppSwIkYuoTA/xMXy01ZwsTkeQ3SGhNy/DtNIom2',2) on conflict (username) do nothing;
INSERT INTO agent (id, username, email, password, authority_id) VALUES 
			(nextval('users_seq'), 'agent1', 'agent1@gmail.com', '$2a$12$SJ9EktANH.ddoAppSwIkYuoTA/xMXy01ZwsTkeQ3SGhNy/DtNIom2',1) on conflict (username) do nothing;
			