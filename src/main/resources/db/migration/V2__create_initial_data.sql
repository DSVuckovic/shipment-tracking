
-- Populating users
INSERT INTO users(id, username, firstname, lastname, email, role, created_at, password)
OVERRIDING SYSTEM VALUE
VALUES (1, 'user1', 'John', 'Smith', 'johnsmith@gmail.com', 'USER', NOW(), '$2a$10$WEoQrbZqW/kpe.s7Dle9UOBh6spv77uq54n2UTWpaHrwB1ZinWFKC'),
       (2, 'user2', 'Jane', 'Smith', 'janesmith@gmail.com', 'USER', NOW(), '$2a$10$WEoQrbZqW/kpe.s7Dle9UOBh6spv77uq54n2UTWpaHrwB1ZinWFKC'),
       (3, 'user3', 'Jack', 'Smith', 'jacksmith@gmail.com', 'USER', NOW(), '$2a$10$WEoQrbZqW/kpe.s7Dle9UOBh6spv77uq54n2UTWpaHrwB1ZinWFKC'),
       (4, 'admin', 'Admin', 'Privileges', 'admin@gmail.com', 'ADMIN', NOW(), '$2a$10$KKlyF4FIBm1W99CO6vspoOtg5k3Yc8j4Qy9rul0tbNJzUd6FZEbgu');

-- Populating shipments
INSERT INTO shipments(id, tracking_number, created_by, description, created_at, status)
    OVERRIDING SYSTEM VALUE
VALUES
    (1, 'TRK-100001', 1, 'Electronics package', NOW(), 2),
    (2, 'TRK-100002', 2, 'Books shipment', NOW(), 1),
    (3, 'TRK-100003', 3, 'Furniture delivery', NOW(), 3),
    (4, 'TRK-100004', 1, 'Clothing package', NOW(), 0);


-- Populating status changes
INSERT INTO status_changes(
    change_id, shipment_id, changed_by, old_status, new_status, changed_at, description)
    OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, 1, 0, 1, NOW(), 'Shipment picked up'),
    (2, 1, 1, 1, 2, NOW(), 'Shipment delivered'),
    (3, 2, 2, 0, 1, NOW(), 'Shipment picked up'),
    (4, 3, 3, 0, 3, NOW(), 'Shipment cancelled');

-- Fixing id sequence
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('shipments_id_seq', (SELECT MAX(id) FROM shipments));
SELECT setval('status_changes_change_id_seq', (SELECT MAX(change_id) FROM status_changes));