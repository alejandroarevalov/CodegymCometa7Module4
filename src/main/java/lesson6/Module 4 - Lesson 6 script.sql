-- Sentencia para crear un esquema
CREATE SCHEMA IF NOT EXISTS cometa7;

-- Sentencia para eliminar un esquema
DROP SCHEMA IF EXISTS cometa7;

-- Selección del esquema a utilizar
USE cometa7;

SELECT e.*
  FROM Estudiantes e;
  
SELECT c.*
  FROM world.Country c;

SELECT a.*
  FROM sakila.address a;

-- Consulta compleja (sin la vista)
SELECT DISTINCT
       consulta_base.email
  FROM (SELECT r.rental_date AS fecha_de_alquiler,
			   dayname(r.rental_date) AS dia,
			   c.email,
			   f.title
		  FROM sakila.rental r
		  JOIN sakila.customer c ON r.customer_id = c.customer_id
		  JOIN sakila.inventory i ON r.inventory_id = i.inventory_id
		  JOIN sakila.film f ON i.film_id = f.film_id
		 WHERE r.rental_date >= '2005-08-01') AS consulta_base
 WHERE consulta_base.dia = 'Friday';
 
-- Crear vista
CREATE VIEW consulta_base AS
SELECT r.rental_date AS fecha_de_alquiler,
	   dayname(r.rental_date) AS dia,
	   c.email,
	   f.title
  FROM sakila.rental r
  JOIN sakila.customer c ON r.customer_id = c.customer_id
  JOIN sakila.inventory i ON r.inventory_id = i.inventory_id
  JOIN sakila.film f ON i.film_id = f.film_id
 WHERE r.rental_date >= '2005-08-01';  
 
-- Consulta compleja 1 (con la vista)
SELECT DISTINCT 
	   cb.email
  FROM consulta_base cb
 WHERE cb.dia = 'Friday';

-- Consulta compleja 2 (con la vista)
SELECT cb.title AS nombre_pelicula,
	   COUNT(cb.title) AS veces_alquilada
  FROM consulta_base cb
 GROUP BY cb.title
 ORDER BY veces_alquilada DESC;
 
-- Sentencia insert para insertar registros en una tabla (sin definir las columnas)
INSERT INTO estudiantes 
     VALUES (4, 'Jonathan', 'Escobar', '1994-06-22', '+513316649977');

-- Sentencia insert para insertar varios registros en una tabla utilizando una única instrucción.
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) 
     VALUES ('Ferenc', 'Yaya Francia', '1992-05-11', '+545551115551'),
            ('Alejandro', 'Arevalo', '1985-06-24', '+573134664466'),
            ('Homero', 'Simpson', '1970-04-10', '+12344564564');
            
-- Sentencia insert basada en una consulta
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento)
SELECT a.first_name,
       a.last_name,
       a.last_update
  FROM sakila.actor a;
  
-- Sentencia update
UPDATE Estudiantes e
   SET e.nombre = lower(e.nombre),
       e.apellido = lower(e.apellido)
 WHERE e.fecha_nacimiento = '2006-02-15';

-- Sentencia update
UPDATE Estudiantes e
   SET e.numero_contacto = null
 WHERE e.fecha_nacimiento = '2006-02-15';
 
-- Sentencia update 
UPDATE Estudiantes e
  JOIN sakila.actor a ON (upper(e.nombre) = a.first_name AND upper(e.apellido) = a.last_name)
   SET a.last_update = NOW()
 WHERE e.fecha_nacimiento = '2006-02-15'
   AND e.nombre LIKE 'a%';
   
-- Sentencia delete
DELETE e
  FROM estudiantes e
 WHERE e.id = 208;
 
-- ALTERAR TABLA -> Agregar columnas
ALTER TABLE estudiantes 
 ADD COLUMN pais INT NULL AFTER numero_contacto;
 
INSERT INTO estudiantes (nombre, apellido, fecha_nacimiento, numero_contacto) 
     VALUES ('Karen', 'Arellano', '1995-12-24', '+528878787878');

UPDATE estudiantes e
   SET e.pais = 60
 WHERE e.id IN (6, 265);

UPDATE estudiantes e
   SET e.pais = 74
 WHERE e.id = 7;
 
SELECT e.*,
	   c.country
  FROM estudiantes e
  LEFT JOIN sakila.country c ON e.pais = c.country_id
 WHERE fecha_nacimiento < '2000-01-01';
 
-- Modificar el nombre una columna
  ALTER TABLE estudiantes 
CHANGE COLUMN pais pais_id INT NULL DEFAULT NULL;

SELECT e.*,
	   c.country
  FROM estudiantes e
  LEFT JOIN sakila.country c ON e.pais_id = c.country_id
 WHERE fecha_nacimiento < '2000-01-01';
 
  ALTER TABLE estudiantes 
MODIFY COLUMN pais_id SMALLINT UNSIGNED NULL DEFAULT NULL;

-- Agregar llave foranea
ALTER TABLE estudiantes 
ADD CONSTRAINT estudiantes_pais_id_sakila_country_id_fk
   FOREIGN KEY (pais_id)
  REFERENCES sakila.country (country_id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

-- Esto no se puede ejecutar por que el campo pais_id esta marcado como UNSIGNED (solo positivos)
UPDATE estudiantes e
   SET e.pais_id = -1
 WHERE e.id = 1;
 
-- Esto no se puede ejecutar por que el campo pais_id debe tener un valor relacionado en el campo
-- sakila.country.country_id
UPDATE estudiantes e
   SET e.pais_id = 5554
 WHERE e.id = 1;
