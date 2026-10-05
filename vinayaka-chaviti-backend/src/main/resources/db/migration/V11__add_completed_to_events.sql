ALTER TABLE events ADD COLUMN completed BOOLEAN NOT NULL DEFAULT FALSE AFTER published;
CREATE INDEX idx_events_completed ON events (completed);