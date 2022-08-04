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
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Veternik', 'Srbija', 'Vojvode Stepe', 0, 0);

INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (1, 'marko@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Marko', 'Marković', '123456789', 'dobar razlog', 'ja sam marko', 1, 1);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (2, 'milan@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Milan', 'Milanović', '987654321', 'dobar razlog', 'ja sam milan', 2, 1);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (3, 'aleksandar@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Aco', 'Acić', '222333444', 'dobar', 3, 2);
INSERT INTO BOAT_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (4, 'lana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', false, 'Lana', 'Lanić', '555666777', 'imam', 4, 3);
INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (5, 'nikola@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Nikola', 'Nikolić', '999888777', true, true, 5, 4);
INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, password_changed, location_id, role_id) VALUES (6, 'jana@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Jana', 'Janić', '123456789', false, false, 6, 4);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (7, 'ralo@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aco', 'Acić', '222333444', 'dobar', 7, 2);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (8, 'ralo1@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Aco', 'Acić', '222333444', 'dobar', 7, 2);