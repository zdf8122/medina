create table t_cms_article(
id integer auto_increment primary key,
create_time date,
create_user integer,
last_update_time date,
last_update_user integer,
channel varchar(200),
content varchar(2000),
title varchar(2000)
);