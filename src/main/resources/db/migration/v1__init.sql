create table customers
(
    id          serial primary key,
    first_name  varchar(255) not null,
    middle_name varchar(255),
    last_name   varchar(255) not null,
    phone       bigint       not null
);