CREATE TYPE userRole as enum ('ADMIN', 'USER');

CREATE TABLE tbl_users
(
    id         SERIAL       NOT NULL PRIMARY KEY,
    first_name varchar(100) NOT NULL,
    last_name  varchar(100) NOT NULL,
    username   varchar(50)  NOT NULL UNIQUE,
    password   varchar(120) NOT NULL,
    user_role  userRole     NOT NULL
)