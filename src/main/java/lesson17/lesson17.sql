CREATE TABLE cuentas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    saldo DECIMAL(15, 2) NOT NULL,
    estudiante_id INT NOT NULL,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_estudiante FOREIGN KEY (estudiante_id)
         REFERENCES estudiantes(id)
         ON DELETE RESTRICT
);

INSERT INTO cuentas (saldo, estudiante_id) VALUES
(100000.00, 1),
(150000.00, 3),
(200000.00, 4),
(180000.00, 5),
(175000.00, 6),
(195000.00, 7),
(130000.00, 8),
(125000.00, 9),
(110000.00, 10),
(140000.00, 11);