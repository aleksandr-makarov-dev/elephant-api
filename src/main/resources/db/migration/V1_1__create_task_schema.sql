create table if not exists tasks
(
    id          bigint generated always as identity,
    title       varchar(100)                not null,
    description varchar(500),
    status      varchar(25)                 not null,
    priority    varchar(25)                 not null,
    created_at  timestamp without time zone not null,
    due_date    timestamp without time zone,
    board_id    bigint                      not null,
    primary key (id),
    constraint fk_board foreign key (board_id) references boards (id)
)