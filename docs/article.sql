create table article
(
    id          bigint unsigned auto_increment
        primary key,
    title       varchar(128)                              not null,
    content     text                                      not null,
    uid         bigint unsigned                           not null,
    category_id bigint unsigned                           not null,
    view_num    bigint unsigned default 0                 not null,
    thumb_num   bigint unsigned default 0                 not null,
    label_ids   varchar(128)    default ''                not null,
    create_time datetime        default CURRENT_TIMESTAMP not null,
    update_time datetime        default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create index idx_category_id
    on code_do.article (category_id);


