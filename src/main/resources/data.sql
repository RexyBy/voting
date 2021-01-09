DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM dishes;
DELETE
FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name)
VALUES ('Friends'),
       ('Best meals'),
       ('Sushi Vesla');

INSERT INTO users (name, email, password, last_time_voted, VOTED_RESTAURANT_ID)
VALUES ('User', 'user@yandex.ru', '{noop}password', '2020-12-22 15:00', 100002),
       ('Admin', 'admin@gmail.com', '{noop}admin', null, null);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100003),
       ('ADMIN', 100004);

INSERT INTO menu (restaurant_id, date, votes)
VALUES ('100000', current_date, 0),
       ('100001', current_date, 2),
       ('100001', '2020-12-22', 0),
       ('100002', current_date, 3),
       ('100002', '2020-12-23', 0);

INSERT INTO dishes (menu_id, name, price)
VALUES ('100005', 'Potato pancakes', 500),
       ('100005', 'Meal soup', 300),
       ('100005', 'Olivie', 100),
       ('100006', 'Steak', 1000),
       ('100006', 'Borscht', 500),
       ('100006', 'Caesar salad', 500),
       ('100007', 'Steak', 1000),
       ('100007', 'Borscht', 500),
       ('100007', 'Caesar salad', 500),
       ('100008', 'Philadelphia', 1500),
       ('100008', 'Tom yam', 1000),
       ('100008', 'Fish salad', 500),
       ('100009', 'Philadelphia', 1500),
       ('100009', 'Tom yam', 1000),
       ('100009', 'Fish salad', 500);