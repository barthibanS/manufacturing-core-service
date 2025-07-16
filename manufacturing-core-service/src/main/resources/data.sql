INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Deborah', 'Collins', 1993);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Alejandro', 'Kennedy', 1979);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Heather', 'Patterson', 1972);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Angel', 'Nelson', 1985);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Justin', 'Lee', 1983);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Kyle', 'Diaz', 1987);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Cory', 'Bennett', 1975);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Julie', 'Morales', 1975);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Molly', 'Wilkerson', 1988);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Jennifer', 'Parrish', 1988);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Holly', 'Floyd', 1967);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Virginia', 'Coleman', 1986);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Jose', 'Benton', 1966);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Christopher', 'Franklin', 1988);
INSERT INTO Person (FIRST_NAME, LAST_NAME, YEAR_OF_BIRTH) VALUES ('Tracy', 'Moore', 1994);

INSERT INTO Device (device_id, type, required_operators) VALUES (2000, 'Conveyor Belt', 2);
INSERT INTO Device (device_id, type, required_operators) VALUES (2001, 'Robotic Arm', 4);
INSERT INTO Device (device_id, type, required_operators) VALUES (2002, 'Welding Unit', 5);
INSERT INTO Device (device_id, type, required_operators) VALUES (2003, 'Sensor', 1);
INSERT INTO Device (device_id, type, required_operators) VALUES (2004, 'Laser Cutter', 4);
INSERT INTO Device (device_id, type, required_operators) VALUES (2005, '3D Printer', 2);
INSERT INTO Device (device_id, type, required_operators) VALUES (2006, 'Packaging Machine', 5);
INSERT INTO Device (device_id, type, required_operators) VALUES (2007, 'Inspection Camera', 4);
INSERT INTO Device (device_id, type, required_operators) VALUES (2008, 'Drill Press', 1);
INSERT INTO Device (device_id, type, required_operators) VALUES (2009, 'Sorting Robot', 1);

INSERT INTO Device_Plant (device_id, plant_key) VALUES (2000, 'PL003');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2001, 'PL003');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2002, 'PL001');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2003, 'PL003');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2004, 'PL003');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2005, 'PL002');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2006, 'PL001');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2007, 'PL002');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2008, 'PL002');
INSERT INTO Device_Plant (device_id, plant_key) VALUES (2009, 'PL002');


INSERT INTO GROUP_ENTITY (name) VALUES ('Logistics Team');
INSERT INTO GROUP_ENTITY (name) VALUES ('Assembly Team');
INSERT INTO GROUP_ENTITY (name) VALUES ('Maintenance Team');

INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2000 FROM GROUP_ENTITY g WHERE g.name = 'Logistics Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2001 FROM GROUP_ENTITY g WHERE g.name = 'Assembly Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2002 FROM GROUP_ENTITY g WHERE g.name = 'Assembly Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2003 FROM GROUP_ENTITY g WHERE g.name = 'Logistics Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2004 FROM GROUP_ENTITY g WHERE g.name = 'Maintenance Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2005 FROM GROUP_ENTITY g WHERE g.name = 'Assembly Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2006 FROM GROUP_ENTITY g WHERE g.name = 'Maintenance Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2007 FROM GROUP_ENTITY g WHERE g.name = 'Maintenance Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2008 FROM GROUP_ENTITY g WHERE g.name = 'Maintenance Team';
INSERT INTO Group_Device (group_id, device_id)
SELECT g.id, 2009 FROM GROUP_ENTITY g WHERE g.name = 'Assembly Team';

INSERT INTO Group_Plant (group_id, plant_key)
SELECT g.id, 'PL002' FROM GROUP_ENTITY g WHERE g.name = 'Assembly Team';
INSERT INTO Group_Plant (group_id, plant_key)
SELECT g.id, 'PL001' FROM GROUP_ENTITY g WHERE g.name = 'Maintenance Team';
INSERT INTO Group_Plant (group_id, plant_key)
SELECT g.id, 'PL002' FROM GROUP_ENTITY g WHERE g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Deborah' AND p.LAST_NAME = 'Collins' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Alejandro' AND p.LAST_NAME = 'Kennedy' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Heather' AND p.LAST_NAME = 'Patterson' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Angel' AND p.LAST_NAME = 'Nelson' AND g.name = 'Maintenance Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Justin' AND p.LAST_NAME = 'Lee' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Kyle' AND p.LAST_NAME = 'Diaz' AND g.name = 'Maintenance Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Cory' AND p.LAST_NAME = 'Bennett' AND g.name = 'Maintenance Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Julie' AND p.LAST_NAME = 'Morales' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Molly' AND p.LAST_NAME = 'Wilkerson' AND g.name = 'Maintenance Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Jennifer' AND p.LAST_NAME = 'Parrish' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Holly' AND p.LAST_NAME = 'Floyd' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Virginia' AND p.LAST_NAME = 'Coleman' AND g.name = 'Assembly Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Jose' AND p.LAST_NAME = 'Benton' AND g.name = 'Logistics Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Christopher' AND p.LAST_NAME = 'Franklin' AND g.name = 'Assembly Team';

INSERT INTO Person_Group (person_id, group_id)
SELECT p.id, g.id
FROM Person p, GROUP_ENTITY g
WHERE p.FIRST_NAME = 'Tracy' AND p.LAST_NAME = 'Moore' AND g.name = 'Logistics Team';

--INSERT INTO Plant (plant_key, location) VALUES ('PL001', 'Moranville');
--INSERT INTO Plant (plant_key, location) VALUES ('PL002', 'Kruegerside');
--INSERT INTO Plant (plant_key, location) VALUES ('PL003', 'Beverlyview');
