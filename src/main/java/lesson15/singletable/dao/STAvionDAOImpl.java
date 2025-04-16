package lesson15.singletable.dao;

import entities.singletable.STAvion;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class STAvionDAOImpl extends AbstractDAO implements STAvionDAO {

    @Override
    public List<STAvion> buscarTodos(int offset, int limit) {
        List<STAvion> aviones = null;
        try(Session session = sessionFactory.openSession()) {
            Query<STAvion> query = session.createQuery("FROM STAvion", STAvion.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            aviones = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de aviones: " + e.getMessage());
        }
        return aviones;
    }

    @Override
    public STAvion buscarPorId(int id) {
        STAvion avion = null;
        try(Session session = sessionFactory.openSession()) {
            avion = session.get(STAvion.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el avión: " + e.getMessage());
        }
        return avion;
    }

    @Override
    public STAvion guardar(STAvion avion) {
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
    public STAvion actualizar(STAvion avion) {
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
    public void eliminar(STAvion avion) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(avion);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar avión: " + e.getMessage());
        }
    }
}
