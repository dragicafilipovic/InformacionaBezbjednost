INSERT INTO users (email,password,certificate,active) VALUES ('user@gmail.com','$2a$10$lFP.ikA43i/ULH0X8m77TOTxqSRtoVslT9agIUM/BjGxbabXSM2H6','user',true);
INSERT INTO users (email,password,certificate,active) VALUES ('admin@gmail.com','$2a$10$lFP.ikA43i/ULH0X8m77TOTxqSRtoVslT9agIUM/BjGxbabXSM2H6','admin',true);

INSERT INTO authority (name) VALUES ('ROLE_USER');
INSERT INTO authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);
