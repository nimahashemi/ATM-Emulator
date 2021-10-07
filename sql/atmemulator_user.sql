create table user
(
    user_id       bigint auto_increment
        primary key,
    birthdate     datetime     null,
    email         varchar(255) null,
    first_name    varchar(255) null,
    gender        varchar(255) null,
    identity      varchar(255) null,
    identity_type varchar(255) null,
    last_name     varchar(255) null,
    mobile        int          null,
    phone_number  int          null,
    status        varchar(255) null,
    constraint UK_982pp5e3eqls127iaiw94bou3
        unique (identity)
)
    engine = MyISAM;

INSERT INTO atmemulator.user (user_id, birthdate, email, first_name, gender, identity, identity_type, last_name, mobile, phone_number, status) VALUES (1, '2021-10-08 01:34:25', null, 'Nima', 'MALE', '0067288987', 'NATIONAL_CARD', 'Hashemi', '9123386725', null, 'ACTIVE');