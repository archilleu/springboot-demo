create table type_dict
(
    id          int auto_increment primary key,
    category    varchar(255) not null comment '分类',
    type_key    int          not null comment '类型key',
    type_value  varchar(255) null comment '类型值',
    is_enable   bit null comment '是否启用',
    create_time datetime null comment '创建时间'
) collate = utf8mb4_general_ci;