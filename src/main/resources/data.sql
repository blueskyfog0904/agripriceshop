INSERT INTO MEMBER(member_id, login_id, pw, user_name, birthdate, gender, tel, addr, email) VALUES(1, 'logintest1', '1234', 'join1', '2023-05-27', '남', '010-1234-1234', '전북 남원시 가방뜰길', 'test@gmail.com');
INSERT INTO MEMBER(member_id, login_id, pw, user_name, birthdate, gender, tel, addr, email) VALUES(2, 'logintest2', '1234', 'join2', '2023-05-27', '남', '010-1234-1234', '전북 남원시 가방뜰길', 'test@gmail.com');
INSERT INTO MEMBER(member_id, login_id, pw, user_name, birthdate, gender, tel, addr, email) VALUES(3, 'logintest3', '1234', 'join3', '2023-05-27', '남', '010-1234-1234', '전북 남원시 가방뜰길', 'test@gmail.com');

INSERT INTO BOARD(BOARD_ID ,BOARD_TITLE ,BOARD_CONTENT ,REGDATE ,VIEW_COUNT ,MEMBER_ID) VALUES (1, '게시글제목1', '게시글 내용1', '2023-06-03T01:34:51.828Z', 0, 1);
INSERT INTO BOARD(BOARD_ID ,BOARD_TITLE ,BOARD_CONTENT ,REGDATE ,VIEW_COUNT ,MEMBER_ID) VALUES (2, '게시글제목2', '게시글 내용2', '2023-06-03T01:34:51.828Z', 0, 1);
INSERT INTO BOARD(BOARD_ID ,BOARD_TITLE ,BOARD_CONTENT ,REGDATE ,VIEW_COUNT ,MEMBER_ID) VALUES (3, '게시글제목3', '게시글 내용3', '2023-06-03T01:34:51.828Z', 0, 1);

INSERT INTO COMMENT(COMMENT_ID ,CM_CONTENT ,REGDATE ,BOARD_ID ,MEMBER_ID) VALUES(1, '댓글 내용1', '2023-06-03T01:34:51.828Z', 1, 1);
INSERT INTO COMMENT(COMMENT_ID ,CM_CONTENT ,REGDATE ,BOARD_ID ,MEMBER_ID) VALUES(2, '댓글 내용2', '2023-06-03T01:34:51.828Z', 1, 1);
INSERT INTO COMMENT(COMMENT_ID ,CM_CONTENT ,REGDATE ,BOARD_ID ,MEMBER_ID) VALUES(3, '댓글 내용3', '2023-06-03T01:34:51.828Z', 1, 1);

INSERT INTO ITEM(ITEM_ID ,NAME ,DESC ,ITEM_CATEGORY ,PRICE ,REGDATE ,STOCK_QUANTITY ,VIEW_COUNT ,MEMBER_ID) VALUES  (1, '사과', '사과 상세내용', 1, 100, '2023-06-03T01:34:51.828Z', 100, 0, 1);
INSERT INTO ITEM(ITEM_ID ,NAME ,DESC ,ITEM_CATEGORY ,PRICE ,REGDATE ,STOCK_QUANTITY ,VIEW_COUNT ,MEMBER_ID) VALUES  (2, '패션후르트', '패션후르트 상세내용', 1, 100, '2023-06-03T01:34:51.828Z', 100, 0, 1);
INSERT INTO ITEM(ITEM_ID ,NAME ,DESC ,ITEM_CATEGORY ,PRICE ,REGDATE ,STOCK_QUANTITY ,VIEW_COUNT ,MEMBER_ID) VALUES  (3, '토마토', '패션후르트 상세내용', 0, 100, '2023-06-03T01:34:51.828Z', 100, 0, 1);
INSERT INTO ITEM(ITEM_ID ,NAME ,DESC ,ITEM_CATEGORY ,PRICE ,REGDATE ,STOCK_QUANTITY ,VIEW_COUNT ,MEMBER_ID) VALUES  (4, '토마토쥬스', '토마토쥬스 상세내용', 3, 100, '2023-06-03T01:34:51.828Z', 100, 0, 1);

