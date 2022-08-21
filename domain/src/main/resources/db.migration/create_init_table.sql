create table members
(
    id            bigint auto_increment,
    email         varchar(50)       not null,
    nickname      varchar(10)       not null,
    password      varchar(100)      not null,
    `delete`      tinyint default 0 not null,
    last_login_at timestamp         null,
    created_at    timestamp         not null,
    update_at     timestamp         not null,
    constraint members_pk
        primary key (id)
);

create unique index members_email_uindex
    on members (email);

create unique index members_nickname_uindex
    on members (nickname);

create table member_detail
(
    id             bigint auto_increment
        primary key,
    profile_image  varchar(200) null,
    status_message varchar(50)  null,
    member_id      bigint       not null,
    constraint UK_b7l4uwe36uauco7y0b2a0f2r3
        unique (member_id),
    constraint FKc9g03bmeofwik6w40mp87esn5
        foreign key (member_id) references members (id)
);

create table direct_message
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    message     varchar(50)  null,
    photo       varchar(200) null,
    receiver_id bigint       null,
    sender_id   bigint       null,
    constraint FKglenxbagc5raie33i5qnye6xc
        foreign key (sender_id) references members (id),
    constraint FKrff940mat5jc6a0u4l15my1ml
        foreign key (receiver_id) references members (id)
);