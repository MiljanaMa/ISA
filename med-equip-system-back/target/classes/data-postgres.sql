--Locations
INSERT INTO public.locations (
    id, city, country, latitude, longitude, postcode, street_number, street
) VALUES ( 1, 'New York', 'USA', 40.7128, -74.0060, 123, '10001', 'Broadway');

INSERT INTO public.locations (
    id, city, country, latitude, longitude, postcode, street_number, street
) VALUES (2, 'London', 'United Kingdom', 51.5074, -0.1278, 456, 'SW1A 1AA', 'Downing Street');

INSERT INTO public.locations (
    id, city, country, latitude, longitude, postcode, street_number, street
) VALUES ( 3, 'Tokyo', 'Japan', 35.6895, 139.6917, 789, '100-0001', 'Chiyoda');

--Companies
INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id, working_hours
) VALUES ( 1, 4.5, 'A leading technology company in New York.', 'Tech Innovators', 1, '8:00-21:00');

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id, working_hours
) VALUES ( 2, 3.8, 'Premier design agency.', 'Creative Solutions', 2, '9:00-20:00');

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id, working_hours
) VALUES ( 3, 4.2, 'Tokyo-based global consulting firm.', 'Global Consultants', 3, '10:00-17:00');


--Loyalty programs
INSERT INTO public.loyalty_programs(
    id, discount, loyalty_type, max_penalty_points, min_points)
VALUES (1, 0, 'NONE', 10, 0);
INSERT INTO public.loyalty_programs(
    id, discount, loyalty_type, max_penalty_points, min_points)
VALUES (2, 5, 'REGULAR', 3, 100);

INSERT INTO public.loyalty_programs(
    id, discount, loyalty_type, max_penalty_points, min_points)
VALUES (3, 7, 'BRONZE', 2, 200);

INSERT INTO public.loyalty_programs(
    id, discount, loyalty_type, max_penalty_points, min_points)
VALUES (4, 10, 'SILVER', 1, 300);
INSERT INTO public.loyalty_programs(
    id, discount, loyalty_type, max_penalty_points, min_points)
VALUES (5, 15, 'GOLD', 0, 400);

--Company equipments
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (1, 10, 'Advanced diagnostic equipment', 'DiagnosX', 5000.00, 0, 1, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (2, 5, 'Life support systems for critical care', 'LifeGuard Pro', 12000.00, 1, 2, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (3, 8, 'Cutting-edge laboratory equipment', 'LabTech 2000', 8000.00, 2, 3, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (4, 12, 'High-tech surgical instruments', 'SurgiMate X', 10000.00, 3, 1, 5);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (5, 7, 'Versatile medical equipment', 'MediPro', 6000.00, 4, 3, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (6, 500, 'Disposable Syringes', 'SyringeMaster 500', 2.50, 4, 1, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (7, 1000, 'Sterile Needles', 'NeedleSafe Pro', 1.50, 4, 2, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (8, 2000, 'Medical Cotton', 'CottonComfort', 0.75, 4, 3, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (9, 50, 'First Aid Kits', 'SafetyKit Plus', 15.00, 4, 1, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (10, 20, 'Digital Thermometers', 'ThermoTech Pro', 25.00, 4, 3, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (11, 30, 'Bandages', 'HealBand Pro', 5.00, 4, 1, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (12, 15, 'Gauze Pads', 'GauzeGuard', 3.00, 4, 2, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (13, 10, 'Medical Scissors', 'ScissorTech', 8.00, 4, 3, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (14, 40, 'Pain Relievers', 'PainAway', 7.00, 4, 1, 0);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id, reserved_count)
VALUES (15, 25, 'Hand Sanitizers', 'SafeHands', 3.50, 4, 3, 0);

--Roles
INSERT INTO public.roles(id, name)
    VALUES (1, 'ROLE_SYSADMIN');

INSERT INTO public.roles(id, name)
	VALUES (2, 'ROLE_COMPADMIN');

INSERT INTO public.roles(id, name)
	VALUES (3, 'ROLE_CLIENT');

-- User 1
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (1, 'New York', 'USA', 'admin@email.com', true, 'SystemAdmin', 'Adminovic', '2020-06-22 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '123456789', 1);

-- User 2
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
     (2, 'New York', 'USA', 'miljana.marjanovic9@gmail.com', true, 'Miljana', 'Johnson', '2020-06-22 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '123456789', 3);

-- User 3
INSERT INTO public.users(
     id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (3, 'Berlin', 'Germany', 'praska@email.com', true, 'Praska', 'Praska', '2020-06-22 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra',  '5551234567', 3);

-- User 4
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (4, 'London', 'UK', 'milenamarkovickg@gmail.com', true, 'Milena', 'Markovic', '2020-06-22 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '987654321', 3);

-- User 5 - client
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (5, 'Tokyo', 'Japan', 'anastano@email.com', true, 'Anastasija', 'Novakovic', '2023-12-12 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '23232323', 3);

-- User 6 - system admin
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (6, 'Tokyo', 'Japan', 'asa@email.com', true, 'Anasta', 'No', '2023-12-12 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '233233434', 1);

-- User 7 - company admin
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (7, 'Tokyo', 'Japan', 'aca@email.com', true, 'Anastasija', 'Novakovic', '2023-12-12 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '23323555', 2);

-- User 8 - company admin
INSERT INTO public.users(
    id, city, country, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, role_id)
VALUES
    (8, 'Tokyo', 'Japan', 'proba@email.com', true, 'Maki', 'Zenin', '2023-12-12 19:10:25-07', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', '23323555', 2);


--System Admin 1
INSERT INTO public.system_admins(
	id, user_id, is_main, is_initial_password_changed)
	VALUES (1, 1, true, true);

--System Admin 2
INSERT INTO public.system_admins(
	id, user_id, is_main, is_initial_password_changed)
	VALUES (2, 6, false, true);
--Client 1
INSERT INTO public.clients(
	id, hospital_info, job_title, penal_points, points, user_id)
	VALUES (1, 'General hospital', 'Doctor', 0, 0, 2);
--Client 2
INSERT INTO public.clients(
	id, hospital_info, job_title, penal_points, points, user_id)
	VALUES (2, 'General hospital', 'Nurse', 0, 0, 3);
--Client 3
INSERT INTO public.clients(
	id, hospital_info, job_title, penal_points, points, user_id)
	VALUES (3, 'General hospital', 'Surgeon', 4, 0, 4);
--Client 4
INSERT INTO public.clients(
	id, hospital_info, job_title, penal_points, points, user_id)
	VALUES (4, 'General hospital', 'Radiologist', 0, 0, 5);


--Company Admins -- this needs to be changed, user composition
--INSERT INTO public.company_admins(
--    id, city, country, email, first_name, last_name, password, phone_number, company_id)
--VALUES
 --   (1, 'New York', 'USA', 'admin1@company1.com', 'Admin1', 'Smith', 'password1', '123456789', 1),
   -- (2, 'New York', 'USA', 'admin2@company1.com', 'Admin2', 'Johnson', 'password2', '987654321', 1),
    --(3, 'New York', 'USA', 'admin3@company1.com', 'Admin3', 'Williams', 'password3', '555666777', 1);

--INSERT INTO public.company_admins(
 --   id, city, country, email, first_name, last_name, password, phone_number, company_id)
--VALUES
 --   (4, 'London', 'UK', 'admin4@company2.com', 'Admin4', 'Brown', 'password4', '333222111', 2),
  --  (5, 'Berlin', 'Germany', 'admin5@company3.com', 'Admin5', 'Taylor', 'password5', '999888777', 3);

-- Company admin - 1
INSERT INTO public.company_admins(
	id, user_id, company_id, first_time)
	VALUES (1, 7, 1, false);

-- Company admin - 2
INSERT INTO public.company_admins(
	id, user_id, company_id, first_time)
	VALUES (2, 8, null, false);

-- Appointments created by Admin 1
INSERT INTO public.appointments(
    id, date, start_time, end_time, status, admin_id)
VALUES
    (1, '2023-12-20', '09:00', '10:30', 0, 1),
    (2, '2024-01-26', '13:00', '13:30', 0, 1),
    (3, '2023-12-20', '15:00', '15:30', 0, 1);

-- Appointments created by Admin 2
INSERT INTO public.appointments(
    id, date, start_time, end_time, status, admin_id)
VALUES
    (4, '2023-12-23', '09:00', '09:30', 1, 1),
    (5, '2023-12-24', '12:00', '12:30', 1, 1);

-- Appointments created by Admin 1
INSERT INTO public.appointments(
    id, date, start_time, end_time, status, admin_id)
VALUES
    (6, '2024-01-25', '11:00', '11:30', 1, 1),
    (7, '2024-01-26', '14:00', '14:30', 1, 1),
    (8, '2024-01-27', '16:00', '16:30', 1, 1);

-- Appointments created by Admin 4 and 5
INSERT INTO public.appointments(
    id, date, start_time, end_time, status, admin_id)
VALUES
    (9, '2024-11-28', '10:00', '10:30', 1, 1),
    (10, '2024-11-29', '13:00', '13:30', 1, 1);


--INSERT INTO public.reservations (id, status, client_id, appointment_id)
--VALUES
  --  (52, 0, 1, 8);

--INSERT INTO public.reservation_items (id, count, equipment_id, reservation_id)
--VALUES
    --(52, 3, 9, 52);

--Reservations
INSERT INTO public.reservations(
    id, status, appointment_id, client_id)
VALUES (1, 2, 1, 3);
INSERT INTO public.reservations(
    id, status, appointment_id, client_id)
VALUES (2, 0, 2, 3);
INSERT INTO public.reservations(
    id, status, appointment_id, client_id)
VALUES
    (3, 2, 3, 3);
--Reservation items
INSERT INTO public.reservation_items(
    id, count, equipment_id, reservation_id, price)
VALUES (1, 2, 1, 1, 500);
INSERT INTO public.reservation_items(
    id, count, equipment_id, reservation_id, price)
VALUES (2, 5, 4, 2, 1200);
INSERT INTO public.reservation_items(
    id, count, equipment_id, reservation_id, price)
VALUES (3, 1, 1, 3, 345);

