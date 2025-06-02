create table if not exists attachments
(
    id          bigint generated always as identity,
    task_id     bigint not null,
    resource_id bigint not null,
    primary key (id),
    constraint fk_task foreign key (task_id) references tasks (id),
    constraint fk_resource foreign key (resource_id) references resources (id)
)