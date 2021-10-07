create table transactions
(
    transaction_id          bigint auto_increment
        primary key,
    amount                  bigint       null,
    date                    datetime     null,
    destination_card_number bigint       null,
    reason                  varchar(255) null,
    source_card_number      bigint       null,
    transaction_status      varchar(255) null,
    type                    int          null,
    user_id                 bigint       null
)
    engine = MyISAM;

INSERT INTO atmemulator.transactions (transaction_id, amount, date, destination_card_number, reason, source_card_number, transaction_status, type, user_id) VALUES (1, 0, '2021-10-08 01:58:23', null, null, 5894631578022410, 'SUCCESS', 2, 1);
INSERT INTO atmemulator.transactions (transaction_id, amount, date, destination_card_number, reason, source_card_number, transaction_status, type, user_id) VALUES (2, 0, '2021-10-08 02:05:31', null, null, 5894631578022410, 'SUCCESS', 2, 1);
INSERT INTO atmemulator.transactions (transaction_id, amount, date, destination_card_number, reason, source_card_number, transaction_status, type, user_id) VALUES (3, 100, '2021-10-08 02:15:00', null, null, 5894631578022410, 'SUCCESS', 0, 1);