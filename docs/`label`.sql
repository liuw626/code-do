create table label
(
    id          bigint unsigned auto_increment
        primary key,
    title       varchar(64)                        not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);
