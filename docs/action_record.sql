create table action_record
(
    id          bigint unsigned auto_increment
        primary key,
    uid         bigint unsigned                    not null,
    target_type int(5)                             not null comment '目标类型:文章/评论',
    action_type int(5)                             not null comment '点赞/收藏',
    target_id   bigint unsigned                    not null,
    create_time datetime default CURRENT_TIMESTAMP not null,
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);
