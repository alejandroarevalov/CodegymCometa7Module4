-- Tabla para Automóviles con uso de la anotación @MappedSuperclass
CREATE TABLE ms_automoviles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    anio INT,
    puertas INT,
    tiene_airbag BOOLEAN,
    tipo_combustible VARCHAR(50)
);

-- Tabla para Motocicletas con uso de la anotación @MappedSuperclass
CREATE TABLE ms_motocicletas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    anio INT,
    cilindraje INT,
    tipo_suspension VARCHAR(50),
    tiene_maletero BOOLEAN
);

-- Tabla para Aviones con uso de la anotación @MappedSuperclass
CREATE TABLE ms_aviones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    anio INT,
    tipo_motor VARCHAR(50),
    capacidad_pasajeros INT,
    alcance_kilometros FLOAT(10,2)
);

INSERT INTO ms_automoviles (marca, modelo, anio, puertas, tiene_airbag, tipo_combustible) VALUES
('Toyota', 'Corolla', 2020, 4, TRUE, 'Gasolina'),
('Chevrolet', 'Onix', 2019, 4, TRUE, 'Gasolina'),
('Renault', 'Duster', 2021, 5, TRUE, 'Diésel'),
('Tesla', 'Model 3', 2022, 4, TRUE, 'Eléctrico'),
('Hyundai', 'Accent', 2018, 4, FALSE, 'Gasolina');

INSERT INTO ms_motocicletas (marca, modelo, anio, cilindraje, tipo_suspension, tiene_maletero) VALUES
('Yamaha', 'FZ25', 2020, 250, 'Telescópica', TRUE),
('Honda', 'CBR500R', 2021, 471, 'Mono-shock', FALSE),
('Bajaj', 'Dominar 400', 2019, 373, 'Telescópica', TRUE),
('Suzuki', 'Gixxer SF', 2022, 155, 'Mono-shock', FALSE),
('KTM', 'Duke 390', 2023, 373, 'WP Apex', TRUE);

INSERT INTO ms_aviones (marca, modelo, anio, tipo_motor, capacidad_pasajeros, alcance_kilometros) VALUES
('Boeing', '737 MAX', 2018, 'Turbina', 210, 6570.00),
('Airbus', 'A320neo', 2020, 'Turbina', 194, 6850.00),
('Cessna', '172 Skyhawk', 2017, 'Hélice', 4, 1289.00),
('Bombardier', 'CRJ900', 2019, 'Jet', 90, 2998.00),
('Embraer', 'E190-E2', 2021, 'Turbina', 114, 4820.00);

CREATE TABLE st_vehiculos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_vehiculo VARCHAR(31),
    marca VARCHAR(100),
    modelo VARCHAR(100),
    anio INT,
    puertas INT,
    tiene_airbag BOOLEAN,
    tipo_combustible VARCHAR(50),
    cilindraje INT,
    tipo_suspension VARCHAR(50),
    tiene_maletero BOOLEAN,
    tipo_motor VARCHAR(50),
    capacidad_pasajeros INT,
    alcance_kilometros DOUBLE
);

INSERT INTO st_vehiculos (
    tipo_vehiculo, marca, modelo, anio,
    puertas, tiene_airbag, tipo_combustible,
    cilindraje, tipo_suspension, tiene_maletero,
    tipo_motor, capacidad_pasajeros, alcance_kilometros
)
VALUES
('AUTOMOVIL', 'Toyota', 'Corolla', 2020, 4, TRUE, 'Gasolina', NULL, NULL, NULL, NULL, NULL, NULL),
('AUTOMOVIL', 'Chevrolet', 'Sail', 2019, 4, TRUE, 'Gasolina', NULL, NULL, NULL, NULL, NULL, NULL),
('AUTOMOVIL', 'Mazda', '3', 2021, 4, FALSE, 'Gasolina', NULL, NULL, NULL, NULL, NULL, NULL),
('MOTOCICLETA', 'Yamaha', 'FZ', 2022, NULL, NULL, NULL, 150, 'Telescópica', TRUE, NULL, NULL, NULL),
('MOTOCICLETA', 'Honda', 'CBR500', 2021, NULL, NULL, NULL, 500, 'Hidráulica', FALSE, NULL, NULL, NULL),
('MOTOCICLETA', 'Bajaj', 'Pulsar', 2023, NULL, NULL, NULL, 200, 'Telescópica', TRUE, NULL, NULL, NULL),
('AVION', 'Boeing', '737', 2015, NULL, NULL, NULL, NULL, NULL, NULL, 'Turbofan', 180, 5600.50),
('AVION', 'Airbus', 'A320', 2018, NULL, NULL, NULL, NULL, NULL, NULL, 'Turbofan', 160, 6100.75),
('AVION', 'Cessna', 'Citation', 2020, NULL, NULL, NULL, NULL, NULL, NULL, 'Jet', 12, 3500.25);

CREATE TABLE jt_vehiculos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    marca VARCHAR(100),
    modelo VARCHAR(100),
    anio INT
);

CREATE TABLE jt_automoviles (
    vehiculo_id INT PRIMARY KEY,
    puertas INT,
    tiene_airbag BOOLEAN,
    tipo_combustible VARCHAR(50),
    FOREIGN KEY (vehiculo_id) REFERENCES jt_vehiculos(id)
);

CREATE TABLE jt_motocicletas (
    vehiculo_id INT PRIMARY KEY,
    cilindraje INT,
    tipo_suspension VARCHAR(50),
    tiene_maletero BOOLEAN,
    FOREIGN KEY (vehiculo_id) REFERENCES jt_vehiculos(id)
);

CREATE TABLE jt_aviones (
    vehiculo_id INT PRIMARY KEY,
    capacidad_pasajeros INT,
    alcance_kilometros DOUBLE,
    tipo_motor VARCHAR(50),
    FOREIGN KEY (vehiculo_id) REFERENCES jt_vehiculos(id)
);

-- Insertar en jt_vehiculos (padre común)
INSERT INTO jt_vehiculos (marca, modelo, anio) VALUES
('Toyota', 'Corolla', 2020),         -- id = 1
('Hyundai', 'i10', 2021),            -- id = 2
('Kia', 'Rio', 2022),                -- id = 3
('Yamaha', 'FZ25', 2021),            -- id = 4
('Suzuki', 'Gixxer', 2022),          -- id = 5
('Bajaj', 'Pulsar', 2018),           -- id = 6
('Boeing', '737', 2015),             -- id = 7
('Airbus', 'A320', 2017),            -- id = 8
('Cessna', 'Skyhawk', 2023);         -- id = 9

-- Automóviles (usando ids 1, 2, 3)
INSERT INTO jt_automoviles (vehiculo_id, puertas, tiene_airbag, tipo_combustible) VALUES
(1, 4, true, 'Gasolina'),
(2, 4, true, 'Gasolina'),
(3, 2, false, 'Eléctrico');

-- Motocicletas (usando ids 4, 5, 6)
INSERT INTO jt_motocicletas (vehiculo_id, cilindraje, tipo_suspension, tiene_maletero) VALUES
(4, 250, 'Telescópica', true),
(5, 200, 'Monoshock', false),
(6, 150, 'Doble horquilla', true);

-- Aviones (usando ids 7, 8, 9)
INSERT INTO jt_aviones (vehiculo_id, capacidad_pasajeros, alcance_kilometros, tipo_motor) VALUES
(7, 180, 3200, 'Turbofan'),
(8, 220, 3700, 'Turboprop'),
(9, 4, 1200, 'Pistón');
