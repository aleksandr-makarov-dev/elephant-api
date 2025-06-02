create table if not exists resources
(
    id         bigint generated always as identity,
    key        varchar(500) not null,
    name       varchar(100) not null,
    ext        varchar(25)  not null,
    size       bigint       not null,
    mime_type  varchar(25)  not null,
    created_at timestamp without time zone,
    primary key (id)
)