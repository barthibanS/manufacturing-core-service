CREATE TABLE Person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(50),
    LAST_NAME VARCHAR(50),
    YEAR_OF_BIRTH INT
);

CREATE TABLE GROUP_ENTITY (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)
);

--CREATE TABLE Plant (
--    plantKey VARCHAR(50) PRIMARY KEY,
--    location VARCHAR(100)
--);

CREATE TABLE Device (
    device_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    required_operators INT
);

CREATE TABLE Person_Group (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    person_id BIGINT,
    group_id BIGINT,
    FOREIGN KEY (person_id) REFERENCES Person(id),
    FOREIGN KEY (group_id) REFERENCES GROUP_ENTITY(id)
);

CREATE TABLE Group_Plant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT,
    plant_Key VARCHAR(50),
    FOREIGN KEY (group_id) REFERENCES GROUP_ENTITY(id)--,
--    FOREIGN KEY (plantKey) REFERENCES Plant(plantKey)
);

CREATE TABLE Group_Device (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_id BIGINT,
    device_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES GROUP_ENTITY(id),
    FOREIGN KEY (device_id) REFERENCES Device(device_id)
);

CREATE TABLE Device_Plant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id BIGINT,
    plant_key VARCHAR(50),
    FOREIGN KEY (device_id) REFERENCES Device(device_id)--,
--    FOREIGN KEY (plantKey) REFERENCES Plant(plantKey)
);

CREATE TABLE login_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    person_id BIGINT NOT NULL,
    device_id BIGINT NOT NULL,
    login_time TIMESTAMP NOT NULL,
    logout_time TIMESTAMP,
    CONSTRAINT fk_login_person FOREIGN KEY (person_id) REFERENCES person(id),
    CONSTRAINT fk_login_device FOREIGN KEY (device_id) REFERENCES device(device_id)
);