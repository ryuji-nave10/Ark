/* ユーザーマスタ */
CREATE TABLE IF NOT EXISTS m_user (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100),
    user_name VARCHAR(50),
    birthday DATE,
    age INT,
    marriage BOOLEAN,
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS article (
	id INT IDENTITY  PRIMARY KEY,
	name VARCHAR(50),
	title VARCHAR(50),
	theme VARCHAR(50),
	user_name VARCHAR(50),
	overview VARCHAR(200)
);