/* ユーザーマスタのデータ（ADMIN権限） */
INSERT INTO m_user (user_id, password, user_name, birthday, age, marriage, role)
VALUES('yamada@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '船戸龍二', '1996-01-01', 28, false, 'ROLE_ADMIN');

/* ユーザーマスタのデータ（一般権限） */
INSERT INTO m_user (user_id, password, user_name, birthday, age, marriage, role)
VALUES('tamura@xxx.co.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', '1986-11-05', 31, false, 'ROLE_GENERAL');

/* 作品のデータ */
INSERT INTO article (name, title, theme, overview)
VALUES ('船戸龍二', '日常風景', 'APEX', 'レジェンドの普段の１日を描いてみました');