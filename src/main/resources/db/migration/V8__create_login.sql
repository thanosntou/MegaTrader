CREATE TABLE login (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    tenant_id bigint NOT NULL,
    user_id bigint NOT NULL,
    created_on timestamp NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id)
);