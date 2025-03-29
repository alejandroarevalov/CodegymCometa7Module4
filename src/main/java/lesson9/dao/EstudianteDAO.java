package lesson9.dao;

import entities.Estudiante;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EstudianteDAO {

    private SessionFactory sessionFactory;

    public EstudianteDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addPackage("entities")
            .buildSessionFactory();
    }

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            estudiantes = session.createQuery("from Estudiante", Estudiante.class).list();
        }
        return estudiantes;
    }

    public Estudiante obtenerPorId(Integer id) {
        Estudiante estudiante;
        try (Session session = this.sessionFactory.openSession()) {
            estudiante = session.get(Estudiante.class, id);
        }
        return estudiante;
    }

    public List<Estudiante> obtenerPorNombre(String nombre) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Estudiante e WHERE e.nombre = :nombre", Estudiante.class)
                .setParameter("nombre", nombre)
                .getResultList();
        }
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(estudiante);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarEstudiante(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(estudiante);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarEstudiante(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(estudiante);
            transaction.commit();
            return true;
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }
}
