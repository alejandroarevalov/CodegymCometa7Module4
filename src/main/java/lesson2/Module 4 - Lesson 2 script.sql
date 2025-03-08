-- Esta es la consulta más sencilla posible de realizar utilizando MySQL
SELECT 1;

-- Esta es la consulta que enlista todos los registros y columnas/campos de la tabla de paises
SELECT *
  FROM Country;

-- Ejemplo de consulta utilizando la funcion IF
SELECT name,
	   continent,
       region,
       population,
       IF (population > 10000000, 'Pais grande', 'Pais pequeño')
  FROM Country;
  
-- Ejemplo de consulta utilizando funciones IF anidadas
SELECT name,
	   continent,
       region,
       population,
       surfacearea,
       IF (population > 10000000, 'Pais grande (poblacion)', 
			IF(surfacearea >= 100000, 'Pais grande (superficie)', 'Pais pequeño'))
  FROM Country;

-- Ejemplo de consulta utilizando la funcion IFNULL
SELECT code,
	   name,
	   continent,
       region,
       population,
       IFNULL(indepyear, 'No disponible')
  FROM Country;
  
-- Ejemplo de consulta utilizando la funcion NULLIF
SELECT code,
	   name,
	   continent,
       region,
       population,
       localname,
       NULLIF(name, localname)
  FROM Country;
  
-- Ejemplo de consulta utilizando la declaracion CASE (1ra notación)
SELECT code,
	   name,
       continent,
       CASE continent WHEN 'North America' THEN 'América del norte'
					  WHEN 'Europe' THEN 'Europa' 
                      WHEN 'South America' THEN 'América del sur'
                      ELSE continent
	   END AS continente,
       region,
       population,
       localname
  FROM Country;

-- Ejemplo de consulta utilizando la declaracion CASE (2nda notación)
SELECT code,
	   name,
       continent,
       CASE WHEN continent = 'North America' THEN 'América del norte'
			WHEN continent = 'Europe' THEN 'Europa' 
			WHEN continent = 'South America' THEN 'América del sur'
			ELSE continent
	   END AS continente,
       region,
       population,
       localname
  FROM Country;
  
-- ¿Qué es la filosofía DRY en programación? --> Tarea.

-- Ejemplo 1 de instrucción GROUP BY
SELECT continent AS continente,
	   COUNT(continent) AS total
  FROM Country
 GROUP BY continent;
 
-- Ejemplo 2 de instrucción GROUP BY
SELECT region,
	   continent,
       COUNT(*) cuenta 
  FROM Country
 WHERE governmentform = 'Dependent Territory of the UK'
 GROUP BY continent, region;
 
-- Ejemplo de instruccion HAVING
SELECT continent,
	   COUNT(continent) AS total
  FROM Country
 GROUP BY continent
HAVING total > 40; -- HAVING COUNT(continent) > 40
