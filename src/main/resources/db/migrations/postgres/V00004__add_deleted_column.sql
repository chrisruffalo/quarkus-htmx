-- allow logical deletes of the tasks
alter table if exists user_tasks add column deleted boolean default false;
