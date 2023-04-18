-- add the user id to the task, more than one task per user
alter table if exists user_tasks add column userId varchar(255);
