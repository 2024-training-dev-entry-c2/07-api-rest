CREATE TABLE order_has_dishes (
    order_id INT NOT NULL,
    dish_id INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (order_id, dish_id),
    FOREIGN KEY (order_id) REFERENCES ORDERS(id),
    FOREIGN KEY (dish_id) REFERENCES DISHES(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);