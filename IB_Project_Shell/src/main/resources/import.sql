INSERT INTO users (email,password,active) VALUES ('user@gmail.com','$2a$04$LnUooPjQ5EQ/rwXvf2r.QumWv4IlMrf2YRn5o0TLmT3CP8ScEjEWq',true);
INSERT INTO users (email,password,active) VALUES ('admin@gmail.com','$2a$04$LnUooPjQ5EQ/rwXvf2r.QumWv4IlMrf2YRn5o0TLmT3CP8ScEjEWq',true);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
