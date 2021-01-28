The REST API for voting application where users can vote where to have a lunch tonight. Including:

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Each restaurant provides a new menu each day.
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user in a day
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

### Used stack

* Spring Boot
* Spring MVC
* Hibernate
* Spring Data JPA
* H2 database
* Maven

### CURL examples

#### get all users

`curl --location --request GET 'localhost:8080/rest/admin/users' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get user 100003

`curl --location --request GET 'localhost:8080/rest/admin/users/100003' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create user

`curl --location --request POST 'localhost:8080/rest/admin/users' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "NewAdmin",
"email": "newAdmin@yanxe.ru",
"password": "password",
"roles": ["USER", "ADMIN"]
}'`

#### update user 100003

`curl --location --request PUT 'localhost:8080/rest/admin/users/100003' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "NewAdmin",
"email": "updated@yanxe.ru",
"password": "password",
"roles": ["USER", "ADMIN"]
}'`

#### delete user 100003

`curl --location --request DELETE 'localhost:8080/rest/admin/users/100003' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get user by himself

`curl --location --request GET 'localhost:8080/rest/profile' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### update user by himself

`curl --location --request PUT 'localhost:8080/rest/profile' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
--header 'Content-Type: application/json' \
--data-raw '{
"id": 100003,
"name": "user",
"email": "user@yan.ru",
"password": "password",
"roles": ["USER", "ADMIN"]
}'`

#### register user by himself

`curl --location --request POST 'localhost:8080/rest/profile/register' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "NewAdmin",
"email": "newAdmin@yanxe.ru",
"password": "password",
"roles": ["USER", "ADMIN"]
}'`

#### delete user by himself

`curl --location --request DELETE 'localhost:8080/rest/profile' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get all restaurants with a menu for current day by user

`curl --location --request GET 'localhost:8080/rest/profile/restaurants' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get  restaurant 100002 with a menu for current day by user

`curl --location --request GET 'localhost:8080/rest/profile/restaurants/100002' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get all restaurants history from 2020-12-23 to 2021-02-28

`curl --location --request GET 'localhost:8080/rest/profile/restaurants/history?startDate=2020-12-23&endDate=2021-02-28' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get restaurant 100002 history

`curl --location --request GET 'localhost:8080/rest/profile/restaurants/history/100002' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get all restaurants with a menu on 2020-12-23

`curl --location --request GET 'localhost:8080/rest/admin/restaurants?date=2020-12-23' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get restaurant 100002 with a menu on 2020-12-23

`curl --location --request GET 'localhost:8080/rest/admin/restaurants/100002?date=2020-12-23' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create restaurant

`curl --location --request POST 'localhost:8080/rest/admin/restaurants' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "new Restaurant"
}'`

#### update restaurant 100002

`curl --location --request PUT 'localhost:8080/rest/admin/restaurants/100002' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "updated Restaurant"
}'`

#### delete restaurant 100001

`curl --location --request DELETE 'localhost:8080/rest/admin/restaurants/100001' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get an up-to-date menu for restaurant 100001

`curl --location --request GET 'localhost:8080/rest/profile/restaurant/menu?restaurantId=100001' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get all menus for restaurant 100001

`curl --location --request GET 'localhost:8080/rest/profile/restaurant/menu/history?restaurantId=100001' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### vote for menu 100005

`curl --location --request PATCH 'localhost:8080/rest/profile/restaurant/menu/100005' \
--header 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ='`

#### get all menus

`curl --location --request GET 'localhost:8080/rest/admin/restaurant/menu' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get all menus on 2020-12-22

`curl --location --request GET 'localhost:8080/rest/admin/restaurant/menu?date=2020-12-22' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get menu 100006

`curl --location --request GET 'localhost:8080/rest/admin/restaurant/menu/100006' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create a menu for restaurant 100000

`curl --location --request POST 'localhost:8080/rest/admin/restaurant/menu?restaurantId=100000' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '   {    
"date": "2020-12-23",
"votes": 0 }'`

#### update menu 100005

`curl --location --request PUT 'localhost:8080/rest/admin/restaurant/menu/100005' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '   {    
"date": "2021-01-08",
"votes": 5 }'`

#### delete menu 100005

`curl --location --request DELETE 'localhost:8080/rest/admin/restaurant/menu/100005' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### set votes amount for menu 100005

`curl --location --request PATCH 'localhost:8080/rest/admin/restaurant/menu/100005?votes=5' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get all dishes

`curl --location --request GET 'localhost:8080/rest/admin/restaurant/menu/dish' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### get dish 100023

`curl --location --request GET 'localhost:8080/rest/admin/restaurant/menu/dish/100023' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`

#### create a dish for menu 100005

`curl --location --request POST 'localhost:8080/rest/admin/restaurant/menu/dish?menuId=100005' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "New dish",
"price": 900 }'`

#### update dish 100011

`curl --location --request PUT 'localhost:8080/rest/admin/restaurant/menu/dish/100011' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Updated dish",
"price": 5000 }'`

#### delete dish 100011

`curl --location --request DELETE 'localhost:8080/rest/admin/restaurant/menu/dish/100011' \
--header 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'`