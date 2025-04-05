CREATE TABLE contactos_de_emergencia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    numero_contacto VARCHAR(20) NOT NULL
);

CREATE TABLE cursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    numero_creditos INT NOT NULL,
    descripcion VARCHAR(100)
);

ALTER TABLE estudiantes
ADD COLUMN contacto_de_emergencia_id INT NULL AFTER nivel_academico,
ADD INDEX contacto_de_emergencia_id_fk_idx (contacto_de_emergencia_id ASC) VISIBLE;

ALTER TABLE estudiantes
ADD CONSTRAINT contacto_de_emergencia_id_fk
FOREIGN KEY (contacto_de_emergencia_id) REFERENCES contactos_de_emergencia (id);

INSERT INTO cursos (nombre, descripcion, numero_creditos)
VALUES ('Introducción a la Programación', 'Principios básicos de la lógica y sintaxis de programación.', 3),
       ('Estructuras de Datos', 'Uso eficiente de listas, pilas, colas, árboles y grafos.', 4),
       ('Algoritmos y Complejidad', 'Diseño y análisis de algoritmos, notación Big-O.', 4),
       ('Programación Orientada a Objetos', 'Conceptos clave de OOP en Java y otros lenguajes.', 3),
       ('Desarrollo Web con HTML, CSS y JS', 'Construcción de sitios web desde cero.', 3),
       ('Java Avanzado', 'APIs modernas, Streams, Concurrencia y Java moderno.', 4),
       ('Bases de Datos con SQL', 'Diseño y consultas sobre bases de datos relacionales.', 3),
       ('Spring Boot y APIs REST', 'Desarrollo de aplicaciones web con arquitectura REST.', 4),
       ('Testing y Buenas Prácticas', 'JUnit, pruebas unitarias y principios SOLID.', 2),
       ('Estructura de Microservicios', 'Arquitectura distribuida y desacoplada para backend.', 4);

INSERT INTO contactos_de_emergencia (id, nombre, numero_contacto)
VALUES (1, 'Martha Ramírez', '+573011122333'),
       (2, 'José González', '+17864453322'),
       (3, 'Sandra Martínez', '+525512345678'),
       (4, 'Luis Rodríguez', '+34612345678'),
       (5, 'Beatriz Pérez', '+491601234567'),
       (6, 'Oscar Moreno', '+447700900123'),
       (7, 'Claudia Castro', '+5511912345678'),
       (8, 'Carlos Ríos', '+33612345678'),
       (9, 'Daniel López', '+819012345678'),
       (10, 'Paola Torres', '+5491123456789');

UPDATE estudiantes SET contacto_de_emergencia_id = 1 WHERE id = 1;
UPDATE estudiantes SET contacto_de_emergencia_id = 2 WHERE id = 3;
UPDATE estudiantes SET contacto_de_emergencia_id = 3 WHERE id = 4;
UPDATE estudiantes SET contacto_de_emergencia_id = 4 WHERE id = 5;
UPDATE estudiantes SET contacto_de_emergencia_id = 5 WHERE id = 6;
UPDATE estudiantes SET contacto_de_emergencia_id = 6 WHERE id = 7;
UPDATE estudiantes SET contacto_de_emergencia_id = 7 WHERE id = 8;
UPDATE estudiantes SET contacto_de_emergencia_id = 8 WHERE id = 9;
UPDATE estudiantes SET contacto_de_emergencia_id = 9 WHERE id = 10;
UPDATE estudiantes SET contacto_de_emergencia_id = 10 WHERE id = 11;

CREATE TABLE estudiantes_cursos (
    estudiante_id BIGINT,
    curso_id BIGINT,
    PRIMARY KEY (estudiante_id, curso_id),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

INSERT INTO estudiantes_cursos (estudiante_id, curso_id)
     VALUES (1, 2),
            (1, 5),
            (1, 9),
            (3, 1),
            (3, 4),
            (4, 7),
            (5, 2),
            (5, 8),
            (6, 1),
            (6, 6),
            (6, 10),
            (7, 4),
            (7, 5),
            (8, 3),
            (9, 3),
            (9, 6),
            (10, 2),
            (11, 4),
            (11, 7),
            (11, 10);

CREATE TABLE estudiantes_idiomas (
    estudiante_id INT NOT NULL,
    idioma VARCHAR(50) NOT NULL,
    PRIMARY KEY (estudiante_id, idioma),
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id)
);

INSERT INTO estudiantes_idiomas (estudiante_id, idioma) VALUES
(1, 'Español'),
(1, 'Inglés'),
(3, 'Inglés'),
(4, 'Español'),
(4, 'Alemán'),
(5, 'Español'),
(6, 'Portugués'),
(6, 'Inglés'),
(6, 'Español'),
(7, 'Italiano'),
(8, 'Español'),
(8, 'Francés'),
(9, 'Español'),
(10, 'Inglés'),
(10, 'Español'),
(11, 'Francés'),
(11, 'Español');

UPDATE estudiantes e
   SET pais_id = (SELECT id FROM paises p WHERE p.nombre = 'United States')
 WHERE e.id > 9
   AND e.pais_id IS NULL;


