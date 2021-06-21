/*INSERT INTO agent (id, username, email, password) VALUES (nextval('users_seq'), 'agent', 'agent@gmail.com', 'Hello1234#') on conflict (id) do nothing;
INSERT INTO customer (id, username, email, password) VALUES (nextval('users_seq'), 'customer', 'customer@gmail.com', 'Hello1234#') on conflict (id) do nothing;
*/