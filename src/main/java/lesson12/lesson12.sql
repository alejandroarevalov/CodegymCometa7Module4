ALTER TABLE cometa7.estudiantes
CHANGE COLUMN activo activo BIT(1) NULL DEFAULT 1;

ALTER TABLE estudiantes ADD COLUMN nivel_academico VARCHAR(20);

UPDATE estudiantes
SET nivel_academico = (
    CASE FLOOR(RAND() * 6)
        WHEN 0 THEN 'PRIMARIA'
        WHEN 1 THEN 'SECUNDARIA'
        WHEN 2 THEN 'TECNICO'
        WHEN 3 THEN 'UNIVERSITARIO'
        WHEN 4 THEN 'POSTGRADO'
        WHEN 5 THEN 'DOCTORADO'
        END
    );

CREATE TABLE eventos_academicos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    fecha_evento DATE,
    hora_evento TIME,
    timestamp_evento DATETIME,
    timestamp_zonificado TIMESTAMP
);

INSERT INTO eventos_academicos(nombre, fecha_evento, hora_evento, timestamp_evento, timestamp_zonificado)
VALUES ('Conferencia IEEE', '2025-03-28', '14:30:00',
        '2025-03-28 14:30:00', UTC_TIMESTAMP());

ALTER TABLE estudiantes
ADD COLUMN fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN fecha_actualizacion DATETIME DEFAULT CURRENT_TIMESTAMP;

CREATE TABLE documentos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255),
    contenido_largo LONGTEXT,
    imagen LONGBLOB
);
