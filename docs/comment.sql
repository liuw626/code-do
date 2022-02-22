create table comment
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned                    not null,
    content     varchar(2014)                      not null,
    article_id  bigint unsigned                    not null,
    pid         bigint unsigned                    not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);
