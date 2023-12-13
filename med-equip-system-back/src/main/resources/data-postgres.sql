-- User 1
INSERT INTO public.users(
    id, city, country, email, first_name, last_name, password, phone_number, user_type)
VALUES
    (1, 'New York', 'USA', 'miljana@email.com', 'Miljana', 'Johnson', '123', '123456789', 0);

-- User 2
INSERT INTO public.users(
    id, city, country, email, first_name, last_name, password, phone_number, user_type)
VALUES
    (2, 'London', 'UK', 'milena@email.com', 'Milena', 'Markovic', '123', '987654321', 0);

-- User 3
INSERT INTO public.users(
    id, city, country, email, first_name, last_name, password, phone_number, user_type)
VALUES
    (3, 'Berlin', 'Germany', 'praska@email.com', 'Praska', 'Praska', '123',  '5551234567', 0);

--Client 1
INSERT INTO public.clients(
	id, email_confirmed, hospital_info, job_title, penal_points, points, user_id)
	VALUES (1, true, 'General hospital', 'Doctor', 0, 0, 1);
--Client 2
INSERT INTO public.clients(
	id, email_confirmed, hospital_info, job_title, penal_points, points, user_id)
	VALUES (2, true, 'General hospital', 'Nurse', 0, 0, 2);
--Client 3
INSERT INTO public.clients(
	id, email_confirmed, hospital_info, job_title, penal_points, points, user_id)
	VALUES (3, true, 'General hospital', 'Surgeon', 0, 0, 3);

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

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES ( 1, 4.5, 'A leading technology company in New York.', 'Tech Innovators', 1);

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES ( 2, 3.8, 'Premier design agency.', 'Creative Solutions', 2);

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES ( 3, 4.2, 'Tokyo-based global consulting firm.', 'Global Consultants', 3);


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

INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (1, 10, 'Advanced diagnostic equipment', 'DiagnosX', 5000.00, 0, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (2, 5, 'Life support systems for critical care', 'LifeGuard Pro', 12000.00, 1, 2);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (3, 8, 'Cutting-edge laboratory equipment', 'LabTech 2000', 8000.00, 2, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (4, 12, 'High-tech surgical instruments', 'SurgiMate X', 10000.00, 3, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (5, 7, 'Versatile medical equipment', 'MediPro', 6000.00, 4, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (6, 500, 'Disposable Syringes', 'SyringeMaster 500', 2.50, 4, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (7, 1000, 'Sterile Needles', 'NeedleSafe Pro', 1.50, 4, 2);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (8, 2000, 'Medical Cotton', 'CottonComfort', 0.75, 4, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (9, 50, 'First Aid Kits', 'SafetyKit Plus', 15.00, 4, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (10, 20, 'Digital Thermometers', 'ThermoTech Pro', 25.00, 4, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (11, 30, 'Bandages', 'HealBand Pro', 5.00, 4, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (12, 15, 'Gauze Pads', 'GauzeGuard', 3.00, 4, 2);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (13, 10, 'Medical Scissors', 'ScissorTech', 8.00, 4, 3);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (14, 40, 'Pain Relievers', 'PainAway', 7.00, 4, 1);
INSERT INTO public.company_equipments (id, count, description, name, price, type, company_id)
VALUES (15, 25, 'Hand Sanitizers', 'SafeHands', 3.50, 4, 3);


INSERT INTO public.company_admins(
    id, city, country, email, first_name, last_name, password, phone_number, company_id)
VALUES
    (1, 'New York', 'USA', 'admin1@company1.com', 'Admin1', 'Smith', 'password1', '123456789', 1),
    (2, 'New York', 'USA', 'admin2@company1.com', 'Admin2', 'Johnson', 'password2', '987654321', 1),
    (3, 'New York', 'USA', 'admin3@company1.com', 'Admin3', 'Williams', 'password3', '555666777', 1);


INSERT INTO public.company_admins(
    id, city, country, email, first_name, last_name, password, phone_number, company_id)
VALUES
    (4, 'London', 'UK', 'admin4@company2.com', 'Admin4', 'Brown', 'password4', '333222111', 2),
    (5, 'Berlin', 'Germany', 'admin5@company3.com', 'Admin5', 'Taylor', 'password5', '999888777', 3);


-- Appointments created by Admin 1
INSERT INTO public.appointments(
    id, date, end_time, start_time, status, admin_id)
VALUES
    (1, '2023-11-20', '10:00', '11:00', FLOOR(RANDOM() * 2)::INTEGER, 1),
    (2, '2023-11-21', '13:00', '14:00', FLOOR(RANDOM() * 2)::INTEGER, 1),
    (3, '2023-11-22', '15:00', '16:00', FLOOR(RANDOM() * 2)::INTEGER, 1);

-- Appointments created by Admin 2
INSERT INTO public.appointments(
    id, date, end_time, start_time, status, admin_id)
VALUES
    (4, '2023-11-23', '09:00', '10:00', FLOOR(RANDOM() * 2)::INTEGER, 2),
    (5, '2023-11-24', '12:00', '13:00', FLOOR(RANDOM() * 2)::INTEGER, 2);

-- Appointments created by Admin 3
INSERT INTO public.appointments(
    id, date, end_time, start_time, status, admin_id)
VALUES
    (6, '2023-11-25', '11:00', '12:00', FLOOR(RANDOM() * 2)::INTEGER, 3),
    (7, '2023-11-26', '14:00', '15:00', FLOOR(RANDOM() * 2)::INTEGER, 3),
    (8, '2023-11-27', '16:00', '17:00', FLOOR(RANDOM() * 2)::INTEGER, 3);

-- Appointments created by Admin 4 and 5
INSERT INTO public.appointments(
    id, date, end_time, start_time, status, admin_id)
VALUES
    (9, '2023-11-28', '10:00', '11:00', FLOOR(RANDOM() * 2)::INTEGER, 4),
    (10, '2023-11-29', '13:00', '14:00', FLOOR(RANDOM() * 2)::INTEGER, 5);


