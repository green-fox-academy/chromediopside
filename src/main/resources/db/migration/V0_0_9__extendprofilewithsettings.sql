CREATE TABLE settings (
  login VARCHAR(255) NOT NULL,
  enable_notifications BOOLEAN,
  enable_background_sync BOOLEAN,
  max_distance INT,
  PRIMARY KEY (login)
);

CREATE TABLE preferred_language_to_profile (
  settings_id VARCHAR(255) NOT NULL,
  language_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (settings_id) REFERENCES settings (login),
  FOREIGN KEY (language_id) REFERENCES language (language_name),
  PRIMARY KEY (settings_id, language_id)
);
