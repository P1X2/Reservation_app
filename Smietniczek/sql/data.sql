-- Inserting More Users
INSERT INTO users (username, password, email, name, surname, user_status, role, created_at, modified_on)
VALUES
('client1', 'password123', 'client1@example.com', 'John', 'Doe', 'ACTIVE', 'CLIENT', NOW(), NOW()),
('client2', 'password123', 'client2@example.com', 'Mary', 'Jane', 'ACTIVE', 'CLIENT', NOW(), NOW()),
('client3', 'password123', 'client3@example.com', 'Mike', 'Tyson', 'ACTIVE', 'CLIENT', NOW(), NOW()),
('employee1', 'password123', 'employee1@example.com', 'Jane', 'Smith', 'ACTIVE', 'EMPLOYEE', NOW(), NOW()),
('employee2', 'password123', 'employee2@example.com', 'Alice', 'Brown', 'ACTIVE', 'EMPLOYEE', NOW(), NOW()),
('employee3', 'password123', 'employee3@example.com', 'Bob', 'Martin', 'ACTIVE', 'EMPLOYEE', NOW(), NOW()),
('president1', 'password123', 'president1@example.com', 'Alice', 'Johnson', 'ACTIVE', 'PRESIDENT', NOW(), NOW());


-- Inserting More Services
INSERT INTO services (name, description, duration_minutes, price, created_at, modified_on)
VALUES
('Massage', 'A relaxing full-body massage', 60, 80, NOW(), NOW()),
('Waxing', 'Hair removal treatment', 45, 50, NOW(), NOW()),
('Eyelash Extension', 'Professional eyelash extension', 120, 120, NOW(), NOW()),
('Haircut', 'A professional haircut service', 45, 50, NOW(), NOW()),
('Manicure', 'Nail care and beautification', 60, 40, NOW(), NOW()),
('Pedicure', 'Foot care and beautification', 60, 45, NOW(), NOW()),
('Hair Coloring', 'Professional hair coloring services', 90, 100, NOW(), NOW()),
('Facial', 'A relaxing facial treatment', 60, 70, NOW(), NOW()); 									-- UWAGA IMPOSTOR


-- Inserting Appointments with Corrected User IDs
INSERT INTO appointments (appointment_date, created_at, modified_on, status, service_id, client_id, employee_id)
VALUES
-- Future appointments with PENDING_PAYMENT or APPOINTMENT_CONFIRMED
('2024-11-05 10:00:00', NOW(), NOW(), 'PENDING_PAYMENT', 1, 8, 11),   -- Haircut for client1 with employee1
('2024-11-10 12:00:00', NOW(), NOW(), 'APPOINTMENT_CONFIRMED', 2, 9, 12),  -- Manicure for client2 with employee2
('2024-11-15 14:00:00', NOW(), NOW(), 'DONE_PAYMENT', 3, 10, 13),     -- Pedicure for client3 with employee3
('2024-10-05 11:00:00', NOW(), NOW(), 'COMPLETED', 4, 8, 11),        -- Hair Coloring for client1 with employee1
('2024-10-01 09:00:00', NOW(), NOW(), 'COMPLETED', 5, 9, 12),        -- Facial for client2 with employee2
('2024-10-08 15:00:00', NOW(), NOW(), 'CANCELLED', 1, 10, 13);         -- Haircut for client3 with employee3


-- Inserting Reviews for COMPLETED Appointments
INSERT INTO reviews (review_content, rating, created_at, modified_on, appointment_id)
VALUES
('Great service, really happy with my haircut!', 5, NOW(), NOW(), 28),  -- Review for appointment_id 28 (COMPLETED - Haircut)
('The facial was very soothing and refreshing!', 5, NOW(), NOW(), 29);  -- Review for appointment_id 29 (COMPLETED - Facial)

