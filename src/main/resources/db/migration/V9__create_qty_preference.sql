CREATE TABLE qty_preference (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    symbol varchar(250) NOT NULL,
    value int NOT NULL CHECK (value >= 0 and value <=100),

    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_qty_preference_user_id ON qty_preference(user_id);