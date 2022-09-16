create table `test_stream` (
    `id` int (11) not null auto_increment,
    `user_id` varchar (40) not null,
    `group_id` int (11) not null,
    `create_time` datetime not null,
    primary key (`id`),
    key `index_user_id` (`user_id`) using hash
) engine = innodb auto_increment = 1 default charset = utf8;

drop procedure `insert_data`;

delimiter $$
create procedure `insert_data`(in n int)
begin
  declare i int default 1;
    while (i <= n ) do
      insert into test_stream (user_id,group_id,create_time ) values (uuid(),floor(rand() * 100) ,now() );
            set i=i+1;
    end while;
end $$
delimiter ;