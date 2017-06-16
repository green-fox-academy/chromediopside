CREATE TABLE GiTinder_user (
  username VARCHAR(40) NOT NULL,
  accessToken VARCHAR(100) NOT NULL,
  appToken VARCHAR(16) NOT NULL,
  PRIMARY KEY (username)
);

CREATE TABLE GiTinder_profile (
  login VARCHAR(40) NOT NULL,
  avatarUrl VARCHAR(255),
  repos VARCHAR(255),
  PRIMARY KEY (login)
);

CREATE TABLE Language (
  id BIGINT,
  login VARCHAR(40),
  languageName VARCHAR(40),
  PRIMARY KEY (id),
  FOREIGN KEY (login) REFERENCES gitinder_profile (login)
);