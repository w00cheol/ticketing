INSERT INTO MEMBER (
    MEMBER_ID,
    NICKNAME,
    USERNAME
) VALUES (
    default,
    '우떠리닉네임',
    '우떠리유저네임'
);

INSERT INTO CONCERT (
    CNT_SEAT,
    START_DATE,
    START_TIME,
    CONCERT_ID,
    NAME
) VALUES (
    5,
    '2023-07-02',
    '10:23:39.63',
    default,
    '레디스를 써보자'
);

INSERT INTO SEAT (
    NUMBER,
    PRICE,
    CONCERT_ID,
    SEAT_ID,
    STATUS
) VALUES
(1, 10000, 1, default, 'OCCUPIED'),
(2, 10000, 1, default, 'OCCUPIED'),
(3, 10000, 1, default, 'OCCUPIED'),
(4, 10000, 1, default, 'EMPTY'),
(5, 10000, 1, default, 'EMPTY');

INSERT INTO TICKET (
    TOTAL_PRICE,
    MEMBER_ID,
    SEAT_ID,
    TICKET_ID,
    STATUS
)
VALUES
(15000, 1, 1, default, 'PAID'),
(15000, 1, 2, default, 'PAID'),
(15000, 1, 3, default, 'CANCEL');