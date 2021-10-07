create table account
(
    account_id     bigint auto_increment
        primary key,
    account_number bigint       null,
    account_type   varchar(255) null,
    balance        bigint       null,
    status         varchar(255) null,
    user_id        bigint       null,
    constraint UK_66gkcp94endmotfwb8r4ocxm9
        unique (account_number)
)
    engine = MyISAM;

INSERT INTO atmemulator.account (account_id, account_number, account_type, balance, status, user_id) VALUES (1, 3386725, 'CURRENT', 100100, 'ACTIVE', 1);