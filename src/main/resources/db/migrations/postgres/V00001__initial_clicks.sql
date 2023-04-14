-- create table for tracking user clicks
create table user_clicks (
    id varchar(255) not null,
    count bigint default 0,
    primary key (id)
);