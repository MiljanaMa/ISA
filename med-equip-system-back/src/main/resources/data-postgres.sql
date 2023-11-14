INSERT INTO public.locations (
    id, city, country, latitude, longitude, "number", postcode, street
) VALUES (
             1, 'New York', 'USA', 40.7128, -74.0060, 123, '10001', 'Broadway'
         );

INSERT INTO public.locations (
    id, city, country, latitude, longitude, "number", postcode, street
) VALUES (
             2, 'London', 'United Kingdom', 51.5074, -0.1278, 456, 'SW1A 1AA', 'Downing Street'
         );

INSERT INTO public.locations (
    id, city, country, latitude, longitude, "number", postcode, street
) VALUES (
             3, 'Tokyo', 'Japan', 35.6895, 139.6917, 789, '100-0001', 'Chiyoda');

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES (
             1, 4.5, 'A leading technology company in New York.', 'Tech Innovators', 1);

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES (
             2, 3.8, 'Premier design agency.', 'Creative Solutions', 2);

INSERT INTO public.companies (
    id, avg_rate, description, name, loc_id
) VALUES (
    3, 4.2, 'Tokyo-based global consulting firm.', 'Global Consultants', 3);