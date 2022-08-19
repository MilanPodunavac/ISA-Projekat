-- svi korisnici imaju lozinku 123

INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_CLIENT');
INSERT INTO ROLE (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_BOAT_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_FISHING_INSTRUCTOR');

INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Doboj', 'BiH', 'Vojvode Stepe', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Alekse Šantića', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Banja Luka', 'BiH', 'Cara Dušana', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Beograd', 'Srbija', 'Dositejeva', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Nis', 'Srbija', 'Nikole Tesle', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Nis', 'Srbija', 'Vojvode Stepe', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Bijeljina', 'BiH', 'Stepe Stepanovića', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 14', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 16', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 20', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 9', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 10', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 11', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 12', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 13', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Vojvode Stepe 14', 0, 0);

INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (1, 'marko76589@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Marko', 'Marković', '123456789', true, true, 1, 1);
INSERT INTO CLIENT (id, email, password, enabled, first_name, last_name, phone_number, banned, penalty_points, location_id, role_id) VALUES (2, 'lazo@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Lazo', 'Lazić', '111111111', false, 0, 2, 2);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (3, 'jana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Jana', 'Janić', '222222222', 'dobar', 3, 3);
INSERT INTO BOAT_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (4, 'lana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Lana', 'Lanić', '333333333', 'imam', 4, 4);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (5, 'aleksandar.savic99@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aleksandar', 'Savić', '555555555', 'razlog', 'ja sam Aleksandar', 5, 5);
INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (6, 'milan@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Milan', 'Milić', '123456789', false, false, 6, 1);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (7, 'radisic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aleksandar', 'Radisic', '222222222', 'dobar', 8, 3);
INSERT INTO CLIENT (id, email, password, enabled, first_name, last_name, phone_number, banned, penalty_points, location_id, role_id) VALUES (8, 'klijent@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Klijent', 'Klijentic', '111111111', false, 0, 9, 2);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (9, 'pero@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Pero', 'Perić', '888777666', 'dobar razlog', 'ja sam Pero', 7, 5);

INSERT INTO ACCOUNT_DELETION_REQUEST (text, user_id) VALUES ('molim te', 5);

INSERT INTO COTTAGE (id, description, name, rules, location_id, bed_number, room_number, cottage_owner_id, price_per_day) VALUES (1, 'cottage', 'cottage', 'rules', 16, 2, 2, 7, 100);

INSERT INTO AVAILABILITY_PERIOD (id, end_date, start_date, sale_entity_id) VALUES(1, '2022-05-30 00:00:00', '2022-05-1 00:00:00', 1);

INSERT INTO COTTAGE_RESERVATION (id, end_date, start_date, number_of_people, price, reservation_refund, reservation_status, system_charge, availability_period_id, client_id, cottage_id) VALUES (1, '2022-05-10 00:00:00', '2022-05-1 00:00:00', 4, 6, 2, 0, 3, 1, 2, 1);

INSERT INTO COTTAGE_RESERVATION_TAGS (cottage_reservation_id, tags) VALUES (1, 1);

INSERT INTO FISHING_TRIP (cost_per_day, description, equipment, max_people, name, percentage_instructor_keeps_if_reservation_cancelled, rules, fishing_instructor_id, location_id) VALUES (100, 'opis', 'oprema', 5, 'naziv', 0, 'pravila', 5, 10);
INSERT INTO FISHING_TRIP (cost_per_day, description, equipment, max_people, name, percentage_instructor_keeps_if_reservation_cancelled, rules, fishing_instructor_id, location_id) VALUES (200, 'opis2', 'oprema2', 10, 'naziv2', 30, 'pravila2', 5, 11);
INSERT INTO FISHING_TRIP (cost_per_day, description, equipment, max_people, name, percentage_instructor_keeps_if_reservation_cancelled, rules, fishing_instructor_id, location_id) VALUES (150, 'opis3', 'oprema3', 3, 'naziv3', 20, 'pravila3', 9, 12);

INSERT INTO FISHING_TRIP_TAGS (fishing_trip_id, tags) VALUES (1, 2);
INSERT INTO FISHING_TRIP_TAGS (fishing_trip_id, tags) VALUES (2, 0);
INSERT INTO FISHING_TRIP_TAGS (fishing_trip_id, tags) VALUES (2, 1);
INSERT INTO FISHING_TRIP_TAGS (fishing_trip_id, tags) VALUES (3, 3);

INSERT INTO FISHING_TRIP_PICTURE (name, fishing_trip_id) VALUES ('fishing_trip_2.jpg', 1);
INSERT INTO FISHING_TRIP_PICTURE (name, fishing_trip_id) VALUES ('fishing_trip_1.jpg', 1);
INSERT INTO FISHING_TRIP_PICTURE (name, fishing_trip_id) VALUES ('fishing_trip_3.jpg', 2);
INSERT INTO FISHING_TRIP_PICTURE (name, fishing_trip_id) VALUES ('fishing_trip_1.jpg', 3);

INSERT INTO FISHING_INSTRUCTOR_AVAILABLE_PERIOD (available_from, available_to, fishing_instructor_id) VALUES('2023-09-05', '2023-12-05', 5);
INSERT INTO FISHING_INSTRUCTOR_AVAILABLE_PERIOD (available_from, available_to, fishing_instructor_id) VALUES('2023-09-05', '2023-12-05', 9);

INSERT INTO FISHING_TRIP_QUICK_RESERVATION (duration_in_days, max_people, price, start, valid_until_and_including, fishing_trip_id, location_id) VALUES(3, 5, 200, '2023-09-10', '2023-09-08', 1, 13);
INSERT INTO FISHING_TRIP_QUICK_RESERVATION (duration_in_days, max_people, price, start, valid_until_and_including, fishing_trip_id, location_id) VALUES(3, 8, 300, '2023-09-20', '2023-09-08', 2, 14);
INSERT INTO FISHING_TRIP_QUICK_RESERVATION (duration_in_days, max_people, price, start, valid_until_and_including, fishing_trip_id, location_id) VALUES(3, 2, 400, '2023-09-10', '2023-09-08', 3, 15);

INSERT INTO FISHING_TRIP_QUICK_RESERVATION_TAGS (fishing_trip_quick_reservation_id, tags) VALUES (1, 2);
INSERT INTO FISHING_TRIP_QUICK_RESERVATION_TAGS (fishing_trip_quick_reservation_id, tags) VALUES (2, 0);
INSERT INTO FISHING_TRIP_QUICK_RESERVATION_TAGS (fishing_trip_quick_reservation_id, tags) VALUES (3, 3);

INSERT INTO FISHING_TRIP_RESERVATION (duration_in_days, number_of_people, price, start, client_id, fishing_trip_id) VALUES(5, 3, 500, '2023-10-10', 2, 1);
INSERT INTO FISHING_TRIP_RESERVATION (duration_in_days, number_of_people, price, start, client_id, fishing_trip_id) VALUES(2, 9, 400, '2023-10-15', 8, 2);
INSERT INTO FISHING_TRIP_RESERVATION (duration_in_days, number_of_people, price, start, client_id, fishing_trip_id) VALUES(8, 2, 1200, '2023-10-15', 2, 3);

INSERT INTO FISHING_TRIP_RESERVATION_TAGS (fishing_trip_reservation_id, tags) VALUES (1, 2);
INSERT INTO FISHING_TRIP_RESERVATION_TAGS (fishing_trip_reservation_id, tags) VALUES (2, 1);
INSERT INTO FISHING_TRIP_RESERVATION_TAGS (fishing_trip_reservation_id, tags) VALUES (3, 3);

INSERT INTO SUBSCRIBER_FISHING_INSTRUCTOR (client_id, instructor_id) VALUES (2, 5);
INSERT INTO SUBSCRIBER_FISHING_INSTRUCTOR (client_id, instructor_id) VALUES (2, 9);
INSERT INTO SUBSCRIBER_FISHING_INSTRUCTOR (client_id, instructor_id) VALUES (8, 5);
