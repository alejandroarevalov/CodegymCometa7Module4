package lesson15.joined.dao;

import entities.joined.JTVehiculo;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class JTVehiculoDAOImpl extends AbstractDAO implements JTVehiculoDAO {

    @Override
    public <T> List<T> buscarTodosConTipo(Class<T> subtipo, int offset, int limit) {
        List<T> resultados = null;
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery("FROM " + subtipo.getSimpleName(), subtipo);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            resultados = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar elementos de tipo " + subtipo.getSimpleName() + ": " + e.getMessage());
        }
        return resultados;
    }

    public <T> T buscarPorIdConTipo(Class<T> tipo, int id) {
        T entidad = null;
        try (Session session = sessionFactory.openSession()) {
            entidad = session.get(tipo, id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar entidad por ID: " + e.getMessage());
        }
        return entidad;
    }

    public <T> T guardar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar entidad: " + e.getMessage());
        }
        return entidad;
    }

    public <T> T actualizar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            entidad = session.merge(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar entidad: " + e.getMessage());
        }
        return entidad;
    }

    public <T> void eliminar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar entidad: " + e.getMessage());
        }
    }

}
