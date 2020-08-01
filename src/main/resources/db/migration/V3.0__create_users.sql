CREATE TABLE users (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    tenant_id bigint NOT NULL,
    username varchar(250) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(250) NOT NULL UNIQUE,
    enabled boolean NOT NULL DEFAULT TRUE,
    api_key varchar(250),
    api_secret varchar(250),
    client varchar(250),
    created_on timestamp NOT NULL,

    FOREIGN KEY (tenant_id) REFERENCES tenant(id)
);