CREATE TABLE trader_to_admin (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    trader_id bigint NOT NULL UNIQUE,
    admin_id bigint NOT NULL,
    created_on timestamp NOT NULL,

    FOREIGN KEY (trader_id) REFERENCES users(id),
    FOREIGN KEY (admin_id) REFERENCES users(id)
);
