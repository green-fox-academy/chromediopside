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
  languagename VARCHAR(40),
  PRIMARY KEY (languagename)
);

CREATE TABLE LANGUAGE_TO_USER (
  PROFILE_ID VARCHAR(40) NOT NULL,
  LANGUAGE_ID VARCHAR(255) NOT NULL,
  FOREIGN KEY (PROFILE_ID) REFERENCES gitinder_profile (login),
  FOREIGN KEY (LANGUAGE_ID) REFERENCES Language (languagename)
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NO CYCLE;
