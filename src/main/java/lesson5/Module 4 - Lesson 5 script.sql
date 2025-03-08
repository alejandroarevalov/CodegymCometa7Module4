-- Sentencia para crear un esquema
CREATE SCHEMA IF NOT EXISTS cometa7;

-- Seleccionar esquema a utilizar
USE cometa7;

-- Script para crear una tabla
CREATE TABLE IF NOT EXISTS estudiantes (
  id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(45) NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  fecha_nacimiento DATE NOT NULL,
  numero_contacto VARCHAR(20) NULL DEFAULT 'N/A',
  PRIMARY KEY (id)
)
COMMENT = 'Esta tabla guarda informacion de estudiantes	';

-- Consulta general de todos los registros de la tabla
SELECT e.* 
  FROM estudiantes e;
  
-- Sentencia insert (para insertar registros en una tabla
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) 
     VALUES ('Franklin', 'Bermeo', '1993-05-14', '+13024489779');

INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) 
     VALUES ('Ferenc Yaya', 'Francia', '1990-11-11', '+521234566781');  
     
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) 
     VALUES ('Rebecca', 'Vargas', '1994-07-12', null);
     
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento) 
     VALUES ('Enrique', 'Perez', '1963-12-24'); -- Formato de fecha éstandar -> yyyy-MM-dd
  
