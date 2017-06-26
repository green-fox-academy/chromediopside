DELETE FROM swiping
WHERE horizontal_direction = 0;

ALTER TABLE swiping
DROP COLUMN  horizontal_direction;
