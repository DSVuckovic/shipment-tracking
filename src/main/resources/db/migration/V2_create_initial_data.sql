
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
    (1, 'TRK-100001', 1, 'Electronics package', NOW(), 3),
    (2, 'TRK-100002', 2, 'Books shipment', NOW(), 2),
    (3, 'TRK-100003', 3, 'Furniture delivery', NOW(), 4),
    (4, 'TRK-100004', 1, 'Clothing package', NOW(), 1);


-- Populating status changes
INSERT INTO status_changes(
    change_id, shipment_id, changed_by, old_status, new_status, changed_at, description)
    OVERRIDING SYSTEM VALUE
VALUES
    (1, 1, 1, 1, 2, NOW(), 'Shipment picked up'),
    (2, 1, 1, 2, 3, NOW(), 'Shipment delivered'),
    (3, 2, 2, 1, 2, NOW(), 'Shipment picked up'),
    (4, 3, 3, 1, 4, NOW(), 'Shipment cancelled');

