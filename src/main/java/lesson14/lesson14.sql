CREATE TABLE libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    isbn VARCHAR(20),
    fecha_publicacion DATE,
    estado ENUM('DISPONIBLE', 'PRESTADO', 'VENCIDO') DEFAULT 'DISPONIBLE',
    fecha_prestamo DATE,
    fecha_devolucion_esperada DATE,
    estudiante_id BIGINT NOT NULL,
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id)
);

INSERT INTO libros (titulo, isbn, fecha_publicacion, estado, fecha_prestamo, fecha_devolucion_esperada, estudiante_id) VALUES
('Libro Técnico #1', 'ISBN-10001', '2025-02-14', 'VENCIDO', '2025-01-01', '2025-01-04', 10),
('Libro Técnico #2', 'ISBN-10002', '2025-03-11', 'PRESTADO', '2025-03-11', '2025-03-14', 4),
('Libro Técnico #3', 'ISBN-10003', '2025-03-13', 'DISPONIBLE', NULL, NULL, 11),
('Libro Técnico #4', 'ISBN-10004', '2025-03-02', 'PRESTADO', '2025-03-20', '2025-03-23', 4),
('Libro Técnico #5', 'ISBN-10005', '2025-03-13', 'VENCIDO', '2025-01-15', '2025-01-18', 4),
('Libro Técnico #6', 'ISBN-10006', '2025-01-02', 'DISPONIBLE', NULL, NULL, 7),
('Libro Técnico #7', 'ISBN-10007', '2025-02-07', 'VENCIDO', '2025-03-20', '2025-03-23', 9),
('Libro Técnico #8', 'ISBN-10008', '2025-02-08', 'PRESTADO', '2025-02-07', '2025-02-10', 1),
('Libro Técnico #9', 'ISBN-10009', '2025-01-10', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #10', 'ISBN-10010', '2025-03-15', 'DISPONIBLE', NULL, NULL, 8),
('Libro Técnico #11', 'ISBN-10011', '2025-02-02', 'PRESTADO', '2025-03-29', '2025-04-01', 7),
('Libro Técnico #12', 'ISBN-10012', '2025-01-26', 'VENCIDO', '2025-01-04', '2025-01-07', 5),
('Libro Técnico #13', 'ISBN-10013', '2025-03-10', 'DISPONIBLE', NULL, NULL, 8),
('Libro Técnico #14', 'ISBN-10014', '2025-01-24', 'VENCIDO', '2025-02-11', '2025-02-14', 9),
('Libro Técnico #15', 'ISBN-10015', '2025-01-07', 'VENCIDO', '2025-03-16', '2025-03-19', 5),
('Libro Técnico #16', 'ISBN-10016', '2025-03-28', 'VENCIDO', '2025-02-08', '2025-02-11', 9),
('Libro Técnico #17', 'ISBN-10017', '2025-02-01', 'DISPONIBLE', NULL, NULL, 11),
('Libro Técnico #18', 'ISBN-10018', '2025-01-26', 'DISPONIBLE', NULL, NULL, 7),
('Libro Técnico #19', 'ISBN-10019', '2025-02-01', 'PRESTADO', '2025-02-27', '2025-03-02', 3),
('Libro Técnico #20', 'ISBN-10020', '2025-02-23', 'VENCIDO', '2025-03-01', '2025-03-04', 8),
('Libro Técnico #21', 'ISBN-10021', '2025-03-25', 'VENCIDO', '2025-01-27', '2025-01-30', 8),
('Libro Técnico #22', 'ISBN-10022', '2025-01-04', 'PRESTADO', '2025-03-04', '2025-03-07', 6),
('Libro Técnico #23', 'ISBN-10023', '2025-03-25', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #24', 'ISBN-10024', '2025-03-02', 'DISPONIBLE', NULL, NULL, 10),
('Libro Técnico #25', 'ISBN-10025', '2025-01-15', 'DISPONIBLE', NULL, NULL, 5),
('Libro Técnico #26', 'ISBN-10026', '2025-03-02', 'VENCIDO', '2025-01-09', '2025-01-12', 5),
('Libro Técnico #27', 'ISBN-10027', '2025-03-14', 'PRESTADO', '2025-01-02', '2025-01-05', 7),
('Libro Técnico #28', 'ISBN-10028', '2025-03-01', 'DISPONIBLE', NULL, NULL, 1),
('Libro Técnico #29', 'ISBN-10029', '2025-03-05', 'VENCIDO', '2025-03-22', '2025-03-25', 10),
('Libro Técnico #30', 'ISBN-10030', '2025-03-02', 'DISPONIBLE', NULL, NULL, 5),
('Libro Técnico #31', 'ISBN-10031', '2025-01-31', 'PRESTADO', '2025-03-20', '2025-03-23', 10),
('Libro Técnico #32', 'ISBN-10032', '2025-01-12', 'VENCIDO', '2025-03-26', '2025-03-29', 3),
('Libro Técnico #33', 'ISBN-10033', '2025-02-17', 'PRESTADO', '2025-02-10', '2025-02-13', 5),
('Libro Técnico #34', 'ISBN-10034', '2025-03-26', 'DISPONIBLE', NULL, NULL, 11),
('Libro Técnico #35', 'ISBN-10035', '2025-01-27', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #36', 'ISBN-10036', '2025-02-02', 'VENCIDO', '2025-01-29', '2025-02-01', 3),
('Libro Técnico #37', 'ISBN-10037', '2025-02-26', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #38', 'ISBN-10038', '2025-01-15', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #39', 'ISBN-10039', '2025-02-02', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #40', 'ISBN-10040', '2025-03-19', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #41', 'ISBN-10041', '2025-03-06', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #42', 'ISBN-10042', '2025-02-01', 'PRESTADO', '2025-02-19', '2025-02-22', 10),
('Libro Técnico #43', 'ISBN-10043', '2025-01-15', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #44', 'ISBN-10044', '2025-02-11', 'PRESTADO', '2025-01-20', '2025-01-23', 10),
('Libro Técnico #45', 'ISBN-10045', '2025-01-07', 'PRESTADO', '2025-01-13', '2025-01-16', 10),
('Libro Técnico #46', 'ISBN-10046', '2025-03-20', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #47', 'ISBN-10047', '2025-01-05', 'DISPONIBLE', NULL, NULL, 11),
('Libro Técnico #48', 'ISBN-10048', '2025-01-21', 'PRESTADO', '2025-01-12', '2025-01-15', 11),
('Libro Técnico #49', 'ISBN-10049', '2025-01-24', 'VENCIDO', '2025-01-02', '2025-01-05', 5),
('Libro Técnico #50', 'ISBN-10050', '2025-01-21', 'DISPONIBLE', NULL, NULL, 3),
('Libro Técnico #51', 'ISBN-10051', '2025-03-17', 'PRESTADO', '2025-02-10', '2025-02-13', 8),
('Libro Técnico #52', 'ISBN-10052', '2025-01-03', 'VENCIDO', '2025-01-02', '2025-01-05', 9),
('Libro Técnico #53', 'ISBN-10053', '2025-01-29', 'PRESTADO', '2025-01-04', '2025-01-07', 11),
('Libro Técnico #54', 'ISBN-10054', '2025-02-17', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #55', 'ISBN-10055', '2025-03-02', 'VENCIDO', '2025-03-24', '2025-03-27', 11),
('Libro Técnico #56', 'ISBN-10056', '2025-03-31', 'VENCIDO', '2025-03-04', '2025-03-07', 5),
('Libro Técnico #57', 'ISBN-10057', '2025-04-01', 'PRESTADO', '2025-03-27', '2025-03-30', 9),
('Libro Técnico #58', 'ISBN-10058', '2025-01-04', 'DISPONIBLE', NULL, NULL, 7),
('Libro Técnico #59', 'ISBN-10059', '2025-02-10', 'PRESTADO', '2025-01-29', '2025-02-01', 10),
('Libro Técnico #60', 'ISBN-10060', '2025-01-06', 'PRESTADO', '2025-02-28', '2025-03-03', 7),
('Libro Técnico #61', 'ISBN-10061', '2025-01-16', 'VENCIDO', '2025-01-03', '2025-01-06', 10),
('Libro Técnico #62', 'ISBN-10062', '2025-02-03', 'VENCIDO', '2025-02-17', '2025-02-20', 10),
('Libro Técnico #63', 'ISBN-10063', '2025-02-16', 'DISPONIBLE', NULL, NULL, 10),
('Libro Técnico #64', 'ISBN-10064', '2025-01-12', 'PRESTADO', '2025-01-05', '2025-01-08', 1),
('Libro Técnico #65', 'ISBN-10065', '2025-02-22', 'DISPONIBLE', NULL, NULL, 10),
('Libro Técnico #66', 'ISBN-10066', '2025-01-12', 'PRESTADO', '2025-02-27', '2025-03-02', 8),
('Libro Técnico #67', 'ISBN-10067', '2025-03-01', 'PRESTADO', '2025-02-23', '2025-02-26', 4),
('Libro Técnico #68', 'ISBN-10068', '2025-01-25', 'PRESTADO', '2025-01-12', '2025-01-15', 6),
('Libro Técnico #69', 'ISBN-10069', '2025-02-19', 'PRESTADO', '2025-01-01', '2025-01-04', 11),
('Libro Técnico #70', 'ISBN-10070', '2025-03-10', 'PRESTADO', '2025-03-29', '2025-04-01', 10),
('Libro Técnico #71', 'ISBN-10071', '2025-02-20', 'VENCIDO', '2025-01-31', '2025-02-03', 4),
('Libro Técnico #72', 'ISBN-10072', '2025-03-22', 'PRESTADO', '2025-02-21', '2025-02-24', 10),
('Libro Técnico #73', 'ISBN-10073', '2025-03-02', 'DISPONIBLE', NULL, NULL, 7),
('Libro Técnico #74', 'ISBN-10074', '2025-03-05', 'VENCIDO', '2025-02-17', '2025-02-20', 11),
('Libro Técnico #75', 'ISBN-10075', '2025-02-04', 'DISPONIBLE', NULL, NULL, 9),
('Libro Técnico #76', 'ISBN-10076', '2025-02-17', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #77', 'ISBN-10077', '2025-02-14', 'VENCIDO', '2025-01-25', '2025-01-28', 4),
('Libro Técnico #78', 'ISBN-10078', '2025-02-18', 'VENCIDO', '2025-03-06', '2025-03-09', 8),
('Libro Técnico #79', 'ISBN-10079', '2025-03-15', 'VENCIDO', '2025-02-09', '2025-02-12', 10),
('Libro Técnico #80', 'ISBN-10080', '2025-02-04', 'VENCIDO', '2025-01-24', '2025-01-27', 10),
('Libro Técnico #81', 'ISBN-10081', '2025-01-11', 'PRESTADO', '2025-01-17', '2025-01-20', 3),
('Libro Técnico #82', 'ISBN-10082', '2025-03-31', 'VENCIDO', '2025-01-25', '2025-01-28', 7),
('Libro Técnico #83', 'ISBN-10083', '2025-01-19', 'DISPONIBLE', NULL, NULL, 9),
('Libro Técnico #84', 'ISBN-10084', '2025-03-24', 'DISPONIBLE', NULL, NULL, 5),
('Libro Técnico #85', 'ISBN-10085', '2025-03-01', 'DISPONIBLE', NULL, NULL, 10),
('Libro Técnico #86', 'ISBN-10086', '2025-02-14', 'PRESTADO', '2025-03-18', '2025-03-21', 7),
('Libro Técnico #87', 'ISBN-10087', '2025-01-16', 'PRESTADO', '2025-01-05', '2025-01-08', 8),
('Libro Técnico #88', 'ISBN-10088', '2025-03-02', 'DISPONIBLE', NULL, NULL, 11),
('Libro Técnico #89', 'ISBN-10089', '2025-03-08', 'DISPONIBLE', NULL, NULL, 6),
('Libro Técnico #90', 'ISBN-10090', '2025-01-24', 'VENCIDO', '2025-01-13', '2025-01-16', 4),
('Libro Técnico #91', 'ISBN-10091', '2025-03-20', 'PRESTADO', '2025-03-07', '2025-03-10', 1),
('Libro Técnico #92', 'ISBN-10092', '2025-03-24', 'DISPONIBLE', NULL, NULL, 7),
('Libro Técnico #93', 'ISBN-10093', '2025-01-02', 'DISPONIBLE', NULL, NULL, 4),
('Libro Técnico #94', 'ISBN-10094', '2025-02-01', 'DISPONIBLE', NULL, NULL, 9),
('Libro Técnico #95', 'ISBN-10095', '2025-03-03', 'DISPONIBLE', NULL, NULL, 5),
('Libro Técnico #96', 'ISBN-10096', '2025-01-05', 'PRESTADO', '2025-03-14', '2025-03-17', 10),
('Libro Técnico #97', 'ISBN-10097', '2025-01-16', 'VENCIDO', '2025-03-01', '2025-03-04', 1),
('Libro Técnico #98', 'ISBN-10098', '2025-02-05', 'DISPONIBLE', NULL, NULL, 1),
('Libro Técnico #99', 'ISBN-10099', '2025-02-23', 'VENCIDO', '2025-03-21', '2025-03-24', 7),
('Libro Técnico #100', 'ISBN-10100', '2025-01-27', 'PRESTADO', '2025-03-15', '2025-03-18', 8);

ALTER TABLE cometa7.libros DROP FOREIGN KEY libros_ibfk_1;

ALTER TABLE cometa7.libros CHANGE COLUMN estudiante_id estudiante_id INT NULL;

ALTER TABLE cometa7.libros ADD CONSTRAINT libros_ibfk_1
    FOREIGN KEY (estudiante_id) REFERENCES cometa7.estudiantes (id);

UPDATE libros l
SET l.estudiante_id = NULL
WHERE l.estado = 'DISPONIBLE';
