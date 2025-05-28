create table if not exists boards
(
    id          bigint generated always as identity,
    title       varchar(100)                not null,
    description varchar(250),
    status      varchar(25)                 not null,
    created_at  timestamp without time zone not null,
    primary key (id)
);