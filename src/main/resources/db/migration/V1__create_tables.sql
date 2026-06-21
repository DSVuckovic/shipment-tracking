
-- USERS table
CREATE TABLE IF NOT EXISTS users
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    firstname VARCHAR(64) NOT NULL,
    lastname VARCHAR(64) NOT NULL,
    email VARCHAR(128) UNIQUE NOT NULL,
    role VARCHAR(32) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    password VARCHAR(256)
);
-- SHIPMENTS table
CREATE TABLE IF NOT EXISTS shipments
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tracking_number VARCHAR(64) UNIQUE NOT NULL,
    created_by BIGINT,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    status INT NOT NULL,
    CONSTRAINT fk_shipment_created_by FOREIGN KEY (created_by) REFERENCES users (id)
);
-- STATUS CHANGE table
CREATE TABLE IF NOT EXISTS status_changes
(
     change_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     shipment_id BIGINT NOT NULL,
     changed_by BIGINT NOT NULL,
     old_status INT NOT NULL,
     new_status INT NOT NULL,
     changed_at TIMESTAMP NOT NULL DEFAULT NOW(),
     description VARCHAR(255),
     CONSTRAINT fk_status_change_shipment FOREIGN KEY (shipment_id) REFERENCES shipments(id),
     CONSTRAINT fk_status_change_changed_by FOREIGN KEY (changed_by) REFERENCES users(id)
);