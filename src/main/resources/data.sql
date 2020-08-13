/* ユーザーマスタのデータ（ADMIN権限） */
INSERT INTO m_user (user_id, password, user_name, birthday, age, marriage, role)
VALUES('yamada001', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '船戸龍二', '1996-01-01', 28, false, 'ROLE_ADMIN');

/* ユーザーマスタのデータ（一般権限） */
INSERT INTO m_user (user_id, password, user_name, birthday, age, marriage, role)
VALUES('tamura002', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '田村', '1986-11-05', 31, false, 'ROLE_GENERAL');

/* 作品のデータ */
INSERT INTO article (name, title, theme, user_name, overview)
VALUES ('yamada001_1', '日常風景', 'APEX', '船戸龍二', 'レジェンドの普段の１日を描いてみました,レジェンドの普段の１日を描いてみました,レジェンドの普段の１日を描いてみました'),('Nave_1', '戦闘', 'APEX', '龍二', 'レジェンドの普段の１日を描いてみました,レジェンドの普段の１日を描いてみました,レジェンドの普段の１日を描いてみました');

