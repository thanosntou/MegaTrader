CREATE TABLE follower_to_trader (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    follower_id bigint NOT NULL UNIQUE,
    trader_id bigint NOT NULL,
    guide boolean NOT NULL DEFAULT FALSE,
    is_hidden boolean NOT NULL DEFAULT FALSE,
    created_on timestamp NOT NULL,

    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (trader_id) REFERENCES users(id)
);
