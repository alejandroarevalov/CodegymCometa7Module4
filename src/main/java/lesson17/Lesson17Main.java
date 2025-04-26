package lesson17;

import entities.Cuenta;
import entities.Estudiante;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import lesson17.dao.CuentaDAO;
import lesson17.dao.CuentaDAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class Lesson17Main {

    private final CuentaDAO cuentaDAO;

    public Lesson17Main(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    // TODO: Desactivar/Activar nivel de aislamiento por defecto
    @SuppressWarnings("MagicConstant")
    public void ejemploReadUncommitedVsReadCommited(int nivelDeAislamiento) {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();

        Thread t1 = new Thread(() -> {
            Session session = sessionFactory.openSession();
            session.doWork(connection -> {
                connection.setTransactionIsolation(nivelDeAislamiento);
            });
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = session.get(Cuenta.class, 1);
            System.out.println("[T1] Saldo original: " + cuenta.getSaldo());

            cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("100000")));
            cuenta = session.merge(cuenta);
            session.flush();

            System.out.println("[T1] Saldo actualizado SIN commit: " + cuenta.getSaldo());
            // tx.commit(); --> No lo vamos a efectuar para ver efectos de lectura transacciones sin confirmación

            try {
                Thread.sleep(5000); // espera antes del rollback
                tx.rollback(); // nunca se confirma
                System.out.println("[T1] Rollback ejecutado");
            } catch (InterruptedException e) {
                System.err.printf("Error al ejecutar rollback: %s", e.getMessage());
            }
            session.close();
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // Espera a que T1 actualice
                Session session = sessionFactory.openSession();
                session.doWork(connection -> {
                    connection.setTransactionIsolation(nivelDeAislamiento);
                });
                Transaction tx = session.beginTransaction();

                Cuenta cuenta = session.get(Cuenta.class, 1);
                System.out.println("[T2] Saldo leído por T2: " + cuenta.getSaldo());

                tx.commit();
                session.close();
            } catch (InterruptedException e) {
                System.err.printf("Error al ejecutar lectura: %s", e.getMessage());
            }
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.printf("Error al solicitar espera en los hilos t1 y t2: %s\n", e.getMessage());
        }

    }

    // TODO: Explicar las lecturas fantasma.
    public void ejemploRepetableRead() {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();

        Thread t1 = new Thread(() -> {
            Session session = sessionFactory.openSession();
            session.doWork(conn -> conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ));
            Transaction tx = session.beginTransaction();

            Cuenta cuenta1 = session.get(Cuenta.class, 2);
            System.out.println("[T1] Primera lectura de saldo: " + cuenta1.getSaldo());

            try {
                Thread.sleep(20000); // Esperamos que T2 modifique
            } catch (InterruptedException e) {
                System.out.printf("Error al ejecutar lectura de saldo: %s\n", e.getMessage());
            }

            Cuenta cuenta2 = session.get(Cuenta.class, 2);
            System.out.println("[T1] Segunda lectura de saldo: " + cuenta2.getSaldo());

            tx.commit();
            session.close();
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(3000); // Esperamos que T1 lea primero
            } catch (InterruptedException e) {
                System.out.printf("Error en espera de ejecución: %s\n", e.getMessage());
            }

            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = session.get(Cuenta.class, 2);
            cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("150000")));
            session.merge(cuenta);
            session.flush();
            tx.commit();
            session.close();

            System.out.println("[T2] Modificó el saldo y confirmó (tiene 20 segundos para verificar en la base de datos)");
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.printf("Error al solicitar espera en los hilos t1 y t2: %s\n", e.getMessage());
        }
    }

    public void ejemploSerializable() {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();
        Thread t1 = new Thread(() -> {
            Session session = sessionFactory.openSession();
            session.doWork(conn ->
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE)
            );
            Transaction tx = session.beginTransaction();

            List<Cuenta> cuentas = session.createQuery(
                "FROM Cuenta c WHERE c.saldo > 100000", Cuenta.class
            ).getResultList();

            System.out.printf("[T1] Cuentas con saldo > 100000: \n%s\n", cuentas);

            try {
                Thread.sleep(7000); // Esperamos que T2 intente insertar
            } catch (InterruptedException e) {
                System.err.printf("Error al intentar detener el hilo T1: %s\n", e.getMessage());
            }

            tx.commit();
            session.close();
            System.out.println("[T1] terminado");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000); // Esperamos que T1 lea primero
            } catch (InterruptedException e) {
                System.err.printf("Error al intentar detener el hilo T2: %s\n", e.getMessage());
            }
            System.out.println("[T2] iniciado");
            Session session = sessionFactory.openSession();
            session.doWork(conn ->
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE)
            );
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = new Cuenta();
            cuenta.setSaldo(new BigDecimal("120000"));
            Estudiante estudiante = session.get(Estudiante.class, 12);
            cuenta.setEstudiante(estudiante);

            session.persist(cuenta);
            System.out.println("[T2] Insertó nueva cuenta");

            tx.commit();
            session.close();
            System.out.println("[T2] terminado");
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.printf("Error al forzar convergencia de los hilos t1 y t2: %s\n", e.getMessage());
        }
    }

    public void transferirSinACID(int idCuentaOrigen, int idCuentaDestino, BigDecimal monto) {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction(); // // Transacción mal manejada
            Cuenta origen = session.get(Cuenta.class, idCuentaOrigen);
            Cuenta destino = session.get(Cuenta.class, idCuentaDestino);

            origen.setSaldo(origen.getSaldo().subtract(monto));
            session.merge(origen);
            session.flush();

            // Simulamos una falla (ej: corte de red, excepción)
            if (true) {
                throw new RuntimeException("¡Falla en mitad de la transacción!");
            }

            destino.setSaldo(destino.getSaldo().add(monto));
            session.merge(destino);

            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al ejecutar transacción: %s\n", e.getMessage());
            // No hacemos rollback
        }
    }

    public void transferirConACID(int idCuentaOrigen, int idCuentaDestino, BigDecimal monto) {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Cuenta origen = session.get(Cuenta.class, idCuentaOrigen);
            Cuenta destino = session.get(Cuenta.class, idCuentaDestino);
            origen.setSaldo(origen.getSaldo().subtract(monto));
            destino.setSaldo(destino.getSaldo().add(monto));
            session.merge(origen);
            session.flush();
            if (true) {
                throw new RuntimeException("¡Falla en mitad de la transacción!");
            }
            session.merge(destino);
            tx.commit();
        } catch (Exception e) {
            System.err.printf("No se pudo completar la transacción: %s\n", e.getMessage());
            tx.rollback();
        } finally {
            session.close();
        }
    }

    public void ejemploBloqueoOptimista() {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();
        Thread t1 = new Thread(() -> {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = session.get(Cuenta.class, 1);
            System.out.printf("[T1] Leyó saldo: %s\n", cuenta.getSaldo());
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                System.err.println("Error al intentar detener [T1]: %s\n");
            }
            cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("10000")));
            session.merge(cuenta);
            try {
                tx.commit();
                System.out.println("[T1] Commit exitoso");
            } catch (OptimisticLockException e) {
                System.err.println("[T1] Excepción de bloqueo optimista atrapada!");
                tx.rollback();
            }
            session.close();
        });
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.printf("Error al solicitar congelación de [T2]: %s\n", e.getMessage());
            }
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = session.get(Cuenta.class, 1);
            System.out.printf("[T2] Leyó saldo: %s\n", cuenta.getSaldo());

            cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("5000")));
            session.merge(cuenta);

            try {
                tx.commit();
                System.out.println("[T2] Commit exitoso");
            } catch (Exception e) {
                System.out.printf("[T2] Error en commit: %s", e.getMessage());
                tx.rollback();
            }
            session.close();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.println("Error al solicitar convergencia de los hilos t1 y t2");
        }
    }

    public void ejemploBloqueoPesimista() {
        SessionFactory sessionFactory = cuentaDAO.getSessionFactory();

        Thread t1 = new Thread(() -> {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            Cuenta cuenta = session.createQuery(
                    "FROM Cuenta c WHERE c.id = :id", Cuenta.class)
                .setParameter("id", 1)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();

            System.out.println("[T1] Bloqueó cuenta con saldo: " + cuenta.getSaldo());

            try {
                Thread.sleep(20000); // Simula que T1 mantiene el bloqueo un tiempo
            } catch (InterruptedException e) {
                System.err.println("Error al intentar detener [T1]: %s\n");
            }

            cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("10000")));
            session.merge(cuenta);

            tx.commit();
            session.close();
            System.out.println("[T1] Commit exitoso");
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000); // Deja que T1 bloquee primero
            } catch (InterruptedException e) {
                System.err.println("Error al intentar detener [T2]: %s\n");
            }

            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            try {
                Cuenta cuenta = session.createQuery(
                        "FROM Cuenta c WHERE c.id = :id", Cuenta.class)
                    .setParameter("id", 2)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult();

                System.out.println("[T2] Pudo acceder a la cuenta con saldo: " + cuenta.getSaldo());

                cuenta.setSaldo(cuenta.getSaldo().add(new BigDecimal("30000")));
                session.merge(cuenta);

                tx.commit();
                System.out.println("[T2] Commit exitoso");
            } catch (Exception e) {
                System.out.println("[T2] Error al intentar bloquear: " + e.getMessage());
                tx.rollback();
            }
            session.close();
        });

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.printf("Error al solicitar convergencia de [T1] y [T2]: %s\n", e.getMessage());
        }
    }

    public static void main(String[] args) {
        CuentaDAO cuentaDAO = new CuentaDAOImpl();
        Lesson17Main main = new Lesson17Main(cuentaDAO);
        // main.ejemploReadUncommitedVsReadCommited(Connection.TRANSACTION_READ_UNCOMMITTED);
        // main.ejemploReadUncommitedVsReadCommited(Connection.TRANSACTION_READ_COMMITTED);
        // main.ejemploRepetableRead();
        // main.ejemploSerializable();
        // main.transferirSinACID(7, 8, new BigDecimal("30000"));
        // main.transferirConACID(7, 8, new BigDecimal("30000"));
        // main.ejemploBloqueoOptimista();
        main.ejemploBloqueoPesimista();
    }
}
