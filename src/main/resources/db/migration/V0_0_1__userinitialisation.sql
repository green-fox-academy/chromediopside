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
  refreshDate TIMESTAMP,
  PRIMARY KEY (login)
);

CREATE TABLE Language (
  languagename VARCHAR(40),
  PRIMARY KEY (languagename)
);

CREATE TABLE LANGUAGE_TO_USER (
  profile_id VARCHAR(40) NOT NULL,
  language_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (profile_id) REFERENCES gitinder_profile (login),
  FOREIGN KEY (language_id) REFERENCES language (languagename),
  PRIMARY KEY (profile_id, language_id)
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NO CYCLE;
