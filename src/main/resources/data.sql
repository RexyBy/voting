DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM dishes;
DELETE
FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO restaurants (name, votes)
VALUES ('Friends', 0),
       ('Best meals', 0),
       ('Sushi Vesla', 1);

INSERT INTO users (name, email, password, last_time_voted, VOTED_RESTAURANT_ID)
VALUES ('User', 'user@yandex.ru', '{noop}password', '2020-12-22 15:00', 100002),
       ('Admin', 'admin@gmail.com', '{noop}admin', null, null);

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100003),
       ('ADMIN', 100004);


INSERT INTO dishes (restaurant_id, name, price, date)
VALUES ('100000', 'Potato pancakes', 500, '2021-01-07'),
       ('100000', 'Meal soup', 300, '2021-01-07'),
       ('100000', 'Olivie', 100, '2021-01-07'),
       ('100001', 'Steak', 1000, '2021-01-07'),
       ('100001', 'Borscht', 500, '2021-01-07'),
       ('100001', 'Caesar salad', 500, '2021-01-07'),
       ('100001', 'Steak', 1000, '2020-12-22'),
       ('100001', 'Borscht', 500, '2020-12-22'),
       ('100001', 'Caesar salad', 500, '2020-12-22'),
       ('100002', 'Philadelphia', 1500, '2021-01-07'),
       ('100002', 'Tom yam', 1000, '2021-01-07'),
       ('100002', 'Fish salad', 500, '2021-01-07'),
       ('100002', 'Philadelphia', 1500, '2020-12-23'),
       ('100002', 'Tom yam', 1000, '2020-12-23'),
       ('100002', 'Fish salad', 500, '2020-12-23');