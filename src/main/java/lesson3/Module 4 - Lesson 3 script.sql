-- Consulta de múltiples tablas (2, pero pueden ser más), incluyendo alias para las columnas para desambiguación.
SELECT country.name,
	   city.name
  FROM country, city
 WHERE country.name = 'Nicaragua';
 
-- Otra manera de escribir los alias de tablas
SELECT pais.name,
	   ciudad.name
  FROM country pais,
       city ciudad
 WHERE pais.name = 'Nicaragua';

-- Ejemplo de una consulta con emparejamiento de registros con una columna cuyos valores son comunes
SELECT pais.code,
	   pais.name,
       pais.continent,
       pais.region,
       '-------',
       ciudad.*
  FROM country pais,
       city ciudad
 WHERE ciudad.countrycode = pais.code;
 
-- Ejemplo de una consulta con alias de columnas y de tablas
SELECT pais.code AS codigo_pais,
	   pais.name AS nombre_pais,
       pais.continent AS contiente,
       pais.region AS region,
       '-------',
       ciudad.id AS id,
       ciudad.name AS nombre_ciudad,
       ciudad.countrycode AS codigo_pais,
       ciudad.district AS distrito_de_ciudad,
       ciudad.population AS poblacion_ciudad 
  FROM country pais,
       city ciudad
 WHERE ciudad.countrycode = pais.code;
 
 -- Ejemplo de una consulta con emparejamiento de registros a través del operador JOIN.
SELECT pais.code AS codigo_pais,
	   pais.name AS nombre_pais,
       pais.continent AS contiente,
       pais.region AS region,
       '-------',
       ciudad.id AS id,
       ciudad.name AS nombre_ciudad,
       ciudad.countrycode AS codigo_pais,
       ciudad.district AS distrito_de_ciudad,
       ciudad.population AS poblacion_ciudad 
  FROM country pais
  JOIN city ciudad ON ciudad.countrycode = pais.code;

-- Segundo ejemplo de una consulta con emparejamiento de registros a través del operador JOIN.
SELECT pais.code,
	   pais.name,
       pais.capital,
       '------',
       ciudad.name,
       ciudad.id
  FROM country pais
  JOIN city ciudad ON pais.capital = ciudad.id; -- INNER JOIN

-- Ejemplo de una consulta con emparejamiento de registros y 
-- registros sin correspondencia en la tabla que contiene la llave foranea
-- Por izquierda
SELECT pais.code,
	   pais.name,
       pais.capital,
       '------',
       ciudad.name,
       ciudad.id
  FROM country pais
  LEFT JOIN city ciudad ON pais.capital = ciudad.id; -- LEFT OUTER JOIN
  
-- Ejemplo de una consulta con emparejamiento de registros y 
-- registros sin correspondencia en la tabla que contiene la llave foranea
-- Por derecha
SELECT pais.code,
	   pais.name,
       pais.capital,
       '------',
       ciudad.name,
       ciudad.id
  FROM city ciudad
 RIGHT JOIN country pais ON pais.capital = ciudad.id; -- RIGHT OUTER JOIN
 
-- Consulta para extraer el promedio de habitantes en el mundo
-- Ejemplo de consulta con la funcion de agregación "promedio"
SELECT AVG(population) AS promedio_poblacion_por_pais
  FROM country pais;
 
-- Consulta que muestra los paises cuya poblacion es mayor al promedio general mundial.
-- (Ejemplo de subconsultas)
 SELECT pais.name,
        pais.population
   FROM country pais
 WHERE pais.population > (
	SELECT AVG(population) AS promedio_poblacion_por_pais
	  FROM country pais
 )
 ORDER BY pais.population DESC;
  
  



 
 