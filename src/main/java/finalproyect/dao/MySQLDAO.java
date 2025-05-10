package finalproyect.dao;

import entities.ContactoDeEmergencia;
import entities.Cuenta;
import entities.Curso;
import entities.Estudiante;
import entities.Libro;
import entities.Pais;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class MySQLDAO implements GenericDAO {

    protected SessionFactory sessionFactory;

    public MySQLDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addAnnotatedClass(ContactoDeEmergencia.class)
            .addAnnotatedClass(Pais.class)
            .addAnnotatedClass(Curso.class)
            .addAnnotatedClass(Libro.class)
            .addAnnotatedClass(Cuenta.class)
            .addPackage("entities")
            .buildSessionFactory();
    }


    @Override
    public <T> T buscarPorId(Class<T> tipo, Integer id) {
        T resultado = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            resultado = session.find(tipo, id);
            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al buscar entidad con id %d: %s", id, e.getMessage());
            throw e;
        }
        System.out.println("Resultado recuperado desde MYSQL");
        return resultado;
    }

    @Override
    public <T> List<T> buscarTodos(Class<T> tipo) {
        List<T> resultados = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String queryString = String.format("FROM %s", tipo.getSimpleName());
            Query<T> query = session.createQuery(queryString, tipo);
            resultados = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al buscar entidades del tipo %s: %s", tipo.getSimpleName(), e.getMessage());
            throw e;
        }
        System.out.println("Resultados recuperados desde MYSQL");
        return resultados;
    }

    @Override
    public <T> T guardar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entidad);
            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al guardar entidad del tipo %s: %s", entidad.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        System.out.println("Operación de creación ejecutada en MYSQL");
        return entidad;
    }

    @Override
    public <T> T actualizar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            entidad = session.merge(entidad);
            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al actualizar entidad del tipo %s: %s", entidad.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        System.out.println("Operación de actualización ejecutada en MYSQL");
        return entidad;
    }

    @Override
    public <T> void eliminar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(entidad);
            tx.commit();
        } catch (Exception e) {
            System.err.printf("Error al eliminar entidad del tipo %s: %s", entidad.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
        System.out.println("Operación de eliminación ejecutada en MYSQL");
    }
}
