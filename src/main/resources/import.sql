

INSERT INTO client (name, email, is_often) VALUES ('John Doe', 'john.doe@example.com', false);
INSERT INTO client (name, email, is_often) VALUES ('Jane Smith', 'jane.smith@example.com', false);
INSERT INTO client (name, email, is_often) VALUES ('Alice Johnson', 'alice.johnson@example.com', false);

-- Crear datos en la tabla menu
INSERT INTO menu (name) VALUES ('Breakfast');
INSERT INTO menu (name) VALUES  ('Lunch');
INSERT INTO menu (name) VALUES  ('Dinner');

-- Crear datos en la tabla dishfood
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES ('Pancakes', 5.99, true, 1); -- Breakfast
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES('Cafe', 2.99, false, 1); -- Breakfast
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES('Hamburguesa', 8.99, true, 2); -- Lunch
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES('Papas fritas', 3.99, false, 2); -- Lunch
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES('Bistec', 15.99, true, 3); -- Dinner
INSERT INTO dishfood (name, price, is_popular, menu_id) VALUES('Ensalada', 6.99, false, 3); -- Dinner

-- Crear datos en la tabla orders
INSERT INTO orders (client_id, local_date) VALUES(1, '2025-01-05');
INSERT INTO orders (client_id, local_date) VALUES(2, '2025-01-05');
INSERT INTO orders (client_id, local_date) VALUES(3, '2025-01-06');

-- Crear datos en la tabla order_dishfood (relación muchos a muchos)
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (1, 1); -- Order 1 incluye Pancakes
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (1, 2); -- Order 1 incluye Coffee
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (2, 3); -- Order 2 incluye Burger
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (2, 4); -- Order 2 incluye Fries
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (3, 5); -- Order 3 incluye Steak
INSERT INTO order_dishfood (order_id, dishfood_id) VALUES (3, 6); -- Order 3 incluye Salad
