CREATE TABLE tenant (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(250) NOT NULL UNIQUE,
    created_on timestamp NOT NULL
);