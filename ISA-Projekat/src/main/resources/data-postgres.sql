-- svi korisnici imaju lozinku 123

INSERT INTO ROLE (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLE (name) VALUES ('ROLE_CLIENT');
INSERT INTO ROLE (name) VALUES ('ROLE_COTTAGE_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_BOAT_OWNER');
INSERT INTO ROLE (name) VALUES ('ROLE_FISHING_INSTRUCTOR');

INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Doboj', 'BiH', 'Vojvode Stepe', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Novi Sad', 'Srbija', 'Alekse Santica', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Banja Luka', 'BiH', 'Cara Dusana', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Beograd', 'Srbija', 'Dositejeva', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Nis', 'Srbija', 'Nikole Tesle', 0, 0);
INSERT INTO LOCATION (city_name, country_name, street_name, longitude, latitude) VALUES ('Nis', 'Srbija', 'Vojvode Stepe', 0, 0);

INSERT INTO ADMIN (id, email, password, enabled, first_name, last_name, phone_number, main_admin, location_id, role_id) VALUES (1, 'marko76589@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'Marko', 'Marković', '123456789', true, 1, 1);
INSERT INTO CLIENT (id, email, password, enabled, first_name, last_name, phone_number, banned, penalty_points, location_id, role_id) VALUES (2, 'BLA@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'LAZO', 'LAZIC', '111111111', false, 0, 2, 2);
INSERT INTO COTTAGE_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (3, 'aleksandar.savic99@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'miki', 'mikic', '222222222', 'dobar', 3, 3);
INSERT INTO BOAT_OWNER (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, location_id, role_id) VALUES (4, 'lana@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'lana', 'lanic', '333333333', 'imam', 4, 4);
INSERT INTO FISHING_INSTRUCTOR (id, email, password, enabled, first_name, last_name, phone_number, reason_for_registration, biography, location_id, role_id) VALUES (5, 'jana@gmaf.faf', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, 'jana', 'janic', '555555555', 'razlog', 'ja sam jana', 5, 5);

INSERT INTO COTTAGE (id, description, name, rules, location_id, bed_number, room_number, cottage_owner_id) VALUES (1, 'cottage', 'cottage', 'rules', 6, 2, 2, 3);

INSERT INTO AVAILABILITY_PERIOD (id, end_date, start_date, sale_entity_id) VALUES(1, '2022-05-30 00:00:00', '2022-05-1 00:00:00', 1);

INSERT INTO COTTAGE_RESERVATION (id, end_date, start_date, max_people, price, reservation_refund, reservation_status, system_charge, availability_period_id, client_id, cottage_id) VALUES (1, '2022-05-30 00:00:00', '2022-05-1 00:00:00', 4, 6, 2, 0, 3, 1, 2, 1);

INSERT INTO COTTAGE_RESERVATION_TAGS (cottage_reservation_id, tags) VALUES (1, 1);
