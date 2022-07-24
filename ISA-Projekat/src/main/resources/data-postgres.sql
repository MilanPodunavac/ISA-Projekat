INSERT INTO ROLE (name) VALUES ('ROLE_CLIENT');
INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_BOAT_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_FISHING_INSTRUCTOR');

INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Doboj', 'BiH', 'Vojvode Stepe', 0, 0);

INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, location_id) VALUES (1, 'user@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Marko', 'Markovic', '123456789', true, 1);
INSERT INTO CLIENT (id, email, password, enabled, first_name, last_name, phone_number, banned, penalty_points, location_id) VALUES (2, 'BLA@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'LAZO', 'LAZIC', '111111111', false, 0, 1);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id) VALUES (3, 'miki@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'miki', 'mikic', '222222222', 'dobar', 1);
INSERT INTO BOAT_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id) VALUES (4, 'lana@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'lana', 'lanic', '333333333', 'imam', 1);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id) VALUES (5, 'jana@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'jana', 'janic', '555555555', 'razlog', 'ja sam jana', 1);

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 3);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (4, 4);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (5, 5);