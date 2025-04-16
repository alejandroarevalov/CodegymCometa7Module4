package lesson15.mappedsuperclass.dao;

import entities.mappedsuperclass.MSAvion;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MSAvionDAOImpl extends AbstractDAO implements MSAvionDAO {

    @Override
    public List<MSAvion> buscarTodos(int offset, int limit) {
        List<MSAvion> aviones = null;
        try(Session session = sessionFactory.openSession()) {
            Query<MSAvion> query = session.createQuery("FROM MSAvion", MSAvion.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            aviones = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de aviones: " + e.getMessage());
        }
        return aviones;
    }

    @Override
    public MSAvion buscarPorId(int id) {
        MSAvion avion = null;
        try(Session session = sessionFactory.openSession()) {
            avion = session.get(MSAvion.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el avión: " + e.getMessage());
        }
        return avion;
    }

    @Override
    public MSAvion guardar(MSAvion avion) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(avion);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar avión: " + e.getMessage());
        }
        return avion;
    }

    @Override
    public MSAvion actualizar(MSAvion avion) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            avion = session.merge(avion);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar avión: " + e.getMessage());
        }
        return avion;
    }

    @Override
    public void eliminar(MSAvion avion) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(avion);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar avión: " + e.getMessage());
        }
    }
}
