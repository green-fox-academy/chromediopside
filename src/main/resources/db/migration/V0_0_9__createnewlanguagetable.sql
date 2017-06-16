CREATE TABLE Language (
  id BIGINT,
  login VARCHAR(40),
  languageName VARCHAR(40),
  PRIMARY KEY (id),
  FOREIGN KEY (login) REFERENCES gitinder_profile (login)
);