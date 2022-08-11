-- svi korisnici imaju lozinku 123

INSERT INTO ROLE (name) VALUES ('ROLE_FISHING_INSTRUCTOR');
INSERT INTO ROLE (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_BOAT_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');

INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Doboj', 'BiH', 'Vojvode Stepe', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Alekse Šantića', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Banja Luka', 'BiH', 'Cara Dušana', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Beograd', 'Srbija', 'Dositejeva', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Niš', 'Srbija', 'Nikole Tesle', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Beograd', 'Srbija', 'Jovina', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe 14', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe 15', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe 16', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe 17', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe 18', 0, 0);


INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (1, 'marko@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Marko', 'Marković', '123456789', 'dobar razlog', 'ja sam marko', 1, 1);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (2, 'milan@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Milan', 'Milanović', '987654321', 'dobar razlog', 'ja sam milan', 2, 1);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (3, 'aleksandar@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Aco', 'Acić', '222333444', 'dobar', 3, 2);
INSERT INTO BOAT_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (4, 'lana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Lana', 'Lanić', '555666777', 'imam', 4, 3);
INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (5, 'nikola@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Nikola', 'Nikolić', '999888777', true, true, 5, 4);
INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (6, 'jana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Jana', 'Janić', '123456789', false, false, 6, 4);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (7, 'ralo@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aco', 'Acić', '222333444', 'dobar', 7, 2);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (8, 'ralo1@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aco', 'Acić', '222333444', 'dobar', 8, 2);
INSERT INTO CLIENT (id, email, password, enabled, first_name, last_name, phone_number, banned, penalty_points, location_id, role_id) VALUES (9, 'klijent@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Klijent', 'Klijentic', '111111111', false, 0, 9, 2);

INSERT INTO COTTAGE (id, description, name, rules, location_id, bed_number, room_number, cottage_owner_id, price_per_day) VALUES (1, 'cottage', 'cottage', 'rules', 10, 2, 2, 7, 100);

INSERT INTO AVAILABILITY_PERIOD (id, end_date, start_date, sale_entity_id) VALUES(1, '2022-05-30 00:00:00 +4:00', '2022-05-1 00:00:00 +4:00', 1);
INSERT INTO AVAILABILITY_PERIOD (id, end_date, start_date, sale_entity_id) VALUES(2, '2022-06-30 00:00:00 +4:00', '2022-06-1 00:00:00 +4:00', 1);

INSERT INTO FISHING_TRIP (cost_per_day, description, equipment, max_people, name, percentage_instructor_keeps_if_reservation_cancelled, rules, fishing_instructor_id, location_id) VALUES (100, 'opis', 'oprema', 5, 'naziv', 0, 'pravila', 1, 11);

INSERT INTO FISHING_INSTRUCTOR_AVAILABLE_PERIOD (available_from, available_to, fishing_instructor_id) VALUES('2023-09-05', '2023-10-05', 1);
