-- list of tasks, per user
create table user_tasks (
    id varchar(255) not null,
    completed boolean,
    text varchar(255),
    primary key (id)
);