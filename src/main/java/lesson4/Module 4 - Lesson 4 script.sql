-- Operaciones aritmeticas regulares (suma, resta, multiplicación, división, etc)
SELECT c.name,
	   c.surfacearea, -- km^2
       c.population, -- habit
       (c.surfacearea / population) * 1000000 AS metros_cuadrados_X_habitante
  FROM country c
 ORDER BY metros_cuadrados_X_habitante DESC;
 
-- Cantidad de bits como unos en la representación de un número en binario
-- 2 -> 00000010
-- 127 -> 01111111 
SELECT BIT_COUNT(2);
SELECT BIT_COUNT(127);

-- Funciones de redondeo
SELECT c.name,
	   c.lifeexpectancy AS expectativa_de_vida,
       ceiling(c.lifeexpectancy) AS redondeo_hacia_arriba,
       floor(c.lifeexpectancy) AS redondeo_hacia_abajo,
       round(c.lifeexpectancy) AS redondeo_al_mas_cercano,
       truncate(c.lifeexpectancy, 0) AS decimales_truncados,
       rand() AS numero_aleatorio_entre_0_y_1,
       rand(3857351) AS numero_aleatorio_entre_0_y_1_con_semilla
  FROM country c;
  
-- Obtener fecha y hora actual
SELECT NOW() AS fecha_actual;

-- Obtener dia del mes y dias transcurridos del año
SELECT DAY(NOW()) AS dia_del_mes,
	   DAYOFYEAR(NOW()) AS dias_transcurridos_del_año;
       
-- Llamados a funciones dentro de la clausula where
SELECT r.rental_date AS fecha_de_alquiler,
	   dayname(r.rental_date),
       c.email,
       f.title
  FROM sakila.rental r
  JOIN sakila.customer c ON r.customer_id = c.customer_id
  JOIN sakila.inventory i ON r.inventory_id = i.inventory_id
  JOIN sakila.film f ON i.film_id = f.film_id
 WHERE dayname(r.rental_date) IN ('Saturday', 'Sunday');
 
-- Buscar eventos/registros entre fechas
SELECT r.*
  FROM sakila.rental r
 WHERE r.rental_date BETWEEN '2005-05-01 00:00:00' AND '2005-05-31 23:59:59';

-- Calcular la diferencia entre 2 fechas
SELECT r.*,
       DATEDIFF(r.return_date, r.rental_date) AS diferencia_en_dias
  FROM sakila.rental r;

-- Operaciones con cadenas
SELECT c.code,
       c.name,
       length(c.name) AS longitud_nombre_pais,
       concat(c.code, '-', c.name, ';') AS concatenacion,
       lower(c.code) AS codigo_pais_minusculas,
       replace(c.name, 'a', 'X') AS remplazando_aes
  FROM country c;

-- Consulta compleja utilizando JOINS, condicionales, diferencia de fechas, alias.
SELECT r.rental_date AS fecha_de_alquiler,
       r.return_date AS fecha_de_devolucion,
       IF(DATEDIFF(r.return_date, r.rental_date) > 3, CONCAT('Multa a ', c.first_name, ' ', c.last_name), 'Devolucion OK') AS establecer_multa,
       c.email,
       f.title
  FROM sakila.rental r
  JOIN sakila.customer c ON r.customer_id = c.customer_id
  JOIN sakila.inventory i ON r.inventory_id = i.inventory_id
  JOIN sakila.film f ON i.film_id = f.film_id;

