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
-- User 1
INSERT INTO public.users(
    id, city, country, email, first_name, hospital_info, job_title, last_name, password, penal_points, phone_number, user_type, points, email_confirmed)
VALUES
    (1, 'New York', 'USA', 'miljana@email.com', 'Miljana', 'General Hospital', 'Doctor', 'Johnson', '123', 0, '123456789', 0, 450, true);

-- User 2
INSERT INTO public.users(
    id, city, country, email, first_name, hospital_info, job_title, last_name, password, penal_points, phone_number, user_type, points, email_confirmed)
VALUES
    (2, 'London', 'UK', 'milena@email.com', 'Milena', 'City Medical Center', 'Nurse', 'Markovic', '123', 2, '987654321', 0, 150, true);

-- User 3
INSERT INTO public.users(
    id, city, country, email, first_name, hospital_info, job_title, last_name, password, penal_points, phone_number, user_type, points, email_confirmed)
VALUES
    (3, 'Berlin', 'Germany', 'praska@email.com', 'Praska', 'Specialty Clinic', 'Surgeon', 'Praska', '123', 1, '5551234567', 0, 350, true);


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

