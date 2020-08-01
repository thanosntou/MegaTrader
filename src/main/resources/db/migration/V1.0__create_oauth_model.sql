CREATE TABLE auth_client (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id varchar(250) NOT NULL UNIQUE,
    secret varchar(250) NOT NULL
);

CREATE TABLE auth_client_authority (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);

CREATE TABLE auth_client_grant_type (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);

CREATE TABLE auth_client_scope (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);