BEGIN TRANSACTION;

DROP TABLE IF EXISTS url;

CREATE TABLE url(

	url_id SERIAL,
	long_url varchar(2048),
	short_url varchar(5),
	created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	expiration_time TIMESTAMP,

	CONSTRAINT PK_url PRIMARY KEY(url_id)

);