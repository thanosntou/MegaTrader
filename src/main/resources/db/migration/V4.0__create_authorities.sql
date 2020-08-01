CREATE TABLE authorities (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    username varchar(250) NOT NULL,
    authority varchar(50) NOT NULL,

    FOREIGN KEY (username) REFERENCES users(username),

    CONSTRAINT UC_authorities UNIQUE (user_id, authority)
);