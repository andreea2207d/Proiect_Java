CREATE TABLE IF NOT EXISTS user_info (
    id BIGINT NOT NULL AUTO_INCREMENT,
    phone_number VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    birthday_date DATE,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shopping_cart (
    id BIGINT NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    fk_user_info BIGINT,
    fk_shopping_cart BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_info) REFERENCES user_info(id),
    FOREIGN KEY (fk_shopping_cart) REFERENCES shopping_cart(id)
);

CREATE TABLE IF NOT EXISTS clothes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    clothing_name VARCHAR(50) NOT NULL,
    price DOUBLE(100,2) NOT NULL,
    clothing_category VARCHAR(10) NOT NULL,
    size VARCHAR(10) NOT NULL,
    clothing_details VARCHAR(50),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clothing_shopping_cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fk_shopping_cart_id BIGINT NOT NULL,
    fk_clothing_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_shopping_cart_id) REFERENCES shopping_cart(id),
    FOREIGN KEY (fk_clothing_id) REFERENCES clothes(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_status VARCHAR(10) NOT NULL,
    fk_user_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS clothing_order (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fk_order_id BIGINT NOT NULL,
    fk_clothing_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_order_id) REFERENCES orders(id),
    FOREIGN KEY (fk_clothing_id) REFERENCES clothes(id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    review VARCHAR(50) NOT NULL,
    fk_user_id BIGINT NOT NULL,
    fk_clothing_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_id) REFERENCES users(id),
    FOREIGN KEY (fk_clothing_id) REFERENCES clothes(id)
);




