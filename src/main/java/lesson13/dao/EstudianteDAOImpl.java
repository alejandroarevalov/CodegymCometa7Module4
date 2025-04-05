package lesson13.dao;

import entities.Estudiante;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EstudianteDAOImpl extends AbstractDAO implements GenericDAO<Estudiante> {

    @Override
    public List<Estudiante> buscarTodos(int offset, int limit) {
        List<Estudiante> estudiantes = null;
        try(Session session = sessionFactory.openSession()) {
            Query<Estudiante> query = session.createQuery("from Estudiante", Estudiante.class);
            if (offset > 0) {
                query.setFirstResult(offset);
            }
            if (limit > 0) {
                query.setMaxResults(limit);
            }
            estudiantes = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    @Override
    public Estudiante buscarPorId(int id) {
        Estudiante estudiante = null;
        try (Session session = sessionFactory.openSession()) {
            estudiante = session.find(Estudiante.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public Estudiante actualizar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            estudiante = session.merge(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public void eliminar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}
