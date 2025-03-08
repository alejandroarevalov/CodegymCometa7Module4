-- Esto es un comentario de línea

/* Esto es
   un comentario
   de bloque
*/

-- Consultar todos los registros y todas las columnas de la tabla Country
SELECT *
  FROM Country;

-- Consultar todos los registros de la tabla country, 
SELECT name,
	   continent,
       region,
       indepyear,
       code2
  FROM Country;

-- Consultar los paises cuyo continente es igual a 'South America', 
-- y mostrar algunos campos
SELECT name,
	   continent,
       region,
       indepyear,
       code2
  FROM Country
 WHERE continent = "South America"; -- Filtros

-- Consultar los paises cuya poblacion es menor a 10.000.000 de habitantes, 
-- y mostrar algunos campos
SELECT name,
	   population,
	   continent,
       region,
       indepyear,
       code2
  FROM Country
 WHERE population < 10000000; -- Filtros
 
-- Ejemplo de uso de operadores lógicos
-- Consulta para recuperar los paises que estan en Asia, Y cuya
-- Expectativa de vida supera los 75 años
SELECT name,
	   continent,
       region,
       code2,
       lifeexpectancy
  FROM Country
 WHERE continent = 'Asia'
   AND lifeexpectancy > 75;
   
-- Ejemplo de uso de palabra reservada BETWEEN
-- Consulta para recuperar los paises que se encuentran en Europa, Asia y 
SELECT name,
	   continent,
       region,
       code2,
       indepyear
  FROM Country
 WHERE indepyear BETWEEN 1850 AND 1900;
 
-- Ejemplo de uso de palabra reservada IN
-- Consulta para recuperar los paises que se 
-- encuentran en Europa, Oceania o Africa
SELECT name,
	   continent,
       region,
       code2,
       indepyear
  FROM Country
 WHERE continent IN ('Europe', 'Oceania', 'Africa');
 
-- Ejemplo de uso de palabra reservada LIMIT y OFFSET
SELECT code,
       name,
	   continent,
       region,
       indepyear,
       code2
  FROM Country
 LIMIT 20
 OFFSET 10;
 
-- Ejemplo de uso de la palabra reservada DISTINCT
SELECT DISTINCT continent
  FROM Country;
  
-- Ejemplo de uso de la sentencia ORDER BY
SELECT code,
       name,
	   continent,
       region,
       indepyear,
       population, 
       code2
  FROM Country
 ORDER BY population DESC;
 
 -- Ejemplo de uso de la sentencia ORDER BY
SELECT code,
       name,
	   continent,
       region,
       indepyear,
       population, 
       code2
  FROM Country
 WHERE indepyear IS NULL
 ORDER BY indepyear;

 
 
    