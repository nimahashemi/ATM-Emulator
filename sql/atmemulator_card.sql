create table card
(
    card_id        bigint auto_increment
        primary key,
    account_id     bigint       null,
    blocked_reason varchar(255) null,
    card_number    bigint       null,
    cvv            int          null,
    expiry_date    datetime     null,
    pin_one        int          null,
    pin_two        int          null,
    status         varchar(255) null,
    constraint UK_by1nk98m2hq5onhl68bo09sc1
        unique (card_number)
)
    engine = MyISAM;

INSERT INTO atmemulator.card (card_id, account_id, blocked_reason, card_number, cvv, expiry_date, pin_one, pin_two, status) VALUES (1, 1, null, 5894631578022410, 974, '2022-10-08 01:41:00', 1234, 12345, 'ACTIVE');