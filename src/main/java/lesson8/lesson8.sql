CREATE TABLE IF NOT EXISTS paises (
    id SMALLINT UNSIGNED PRIMARY KEY UNIQUE AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL
);

ALTER TABLE estudiantes
DROP FOREIGN KEY estudiantes_pais_id_sakila_country_id_fk;

ALTER TABLE estudiantes
DROP INDEX estudiantes_pais_id_sakila_country_id_fk;

SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE estudiantes
ADD CONSTRAINT estudiantes_pais_id_sakila_country_id_fk
FOREIGN KEY (pais_id) REFERENCES paises(id);

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO paises (nombre, fecha_actualizacion)
SELECT country, last_update
  FROM sakila.country
 ORDER BY country_id;

DELIMITER $$

CREATE PROCEDURE ConvertirCelsiusAFahrenheit(
    IN celsius DECIMAL(10,2),
    OUT fahrenheit DECIMAL(10,2)
)
BEGIN
    SET fahrenheit = (celsius * 9 / 5) + 32;
END $$

DELIMITER ;

CREATE TABLE objetos (
     id INT AUTO_INCREMENT PRIMARY KEY,
     nombre VARCHAR(100),
     datos BLOB
);