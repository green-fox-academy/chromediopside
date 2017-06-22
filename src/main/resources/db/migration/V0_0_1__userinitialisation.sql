CREATE TABLE gitinder_user (
  user_name VARCHAR(40) NOT NULL,
  access_token VARCHAR(100) NOT NULL,
  app_token VARCHAR(16) NOT NULL,
  PRIMARY KEY (user_name)
);

CREATE TABLE gitinder_profile (
  login VARCHAR(40) NOT NULL,
  avatar_url VARCHAR(255),
  repos VARCHAR(255),
  refresh_date TIMESTAMP,
  PRIMARY KEY (login)
);

CREATE TABLE language (
  language_name VARCHAR(40),
  PRIMARY KEY (language_name)
);

CREATE TABLE language_to_profile (
  profile_id VARCHAR(40) NOT NULL,
  language_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (profile_id) REFERENCES gitinder_profile (login),
  FOREIGN KEY (language_id) REFERENCES language (language_name),
  PRIMARY KEY (profile_id, language_id)
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 NO CYCLE;
