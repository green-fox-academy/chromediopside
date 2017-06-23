CREATE TYPE HORIZONTAL_DIRECTION AS ENUM ('LEFT', 'RIGHT');

CREATE TABLE swiping (
  swiping_users_name VARCHAR(40) NOT NULL,
  swiped_users_name VARCHAR(40) NOT NULL,
  horizontal_direction INT NOT NULL,
  FOREIGN KEY (swiping_users_name) REFERENCES gitinder_user (user_name),
  FOREIGN KEY (swiped_users_name) REFERENCES gitinder_user (user_name),
  PRIMARY KEY (swiping_users_name, swiped_users_name)
);