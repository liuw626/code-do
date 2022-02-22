
create table code_do.user
(
    id              bigint unsigned auto_increment
        primary key,
    username        varchar(64)                        not null,
    avatar          varchar(256)                       not null,
    open_id         varchar(128)                       not null,
    ext             json                               null,
    last_login_time datetime default CURRENT_TIMESTAMP not null,
    create_time     datetime default CURRENT_TIMESTAMP not null,
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint uni_open_id
        unique (open_id)
);
