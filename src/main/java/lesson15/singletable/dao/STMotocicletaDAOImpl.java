package lesson15.singletable.dao;

import entities.singletable.STMotocicleta;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class STMotocicletaDAOImpl extends AbstractDAO implements STMotocicletaDAO {

    @Override
    public List<STMotocicleta> buscarTodos(int offset, int limit) {
        List<STMotocicleta> motocicletas = null;
        try(Session session = sessionFactory.openSession()) {
            Query<STMotocicleta> query = session.createQuery("FROM STMotocicleta", STMotocicleta.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            motocicletas = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de motocicletas: " + e.getMessage());
        }
        return motocicletas;
    }

    @Override
    public STMotocicleta buscarPorId(int id) {
        STMotocicleta motocicleta = null;
        try(Session session = sessionFactory.openSession()) {
            motocicleta = session.get(STMotocicleta.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar la motocicleta: " + e.getMessage());
        }
        return motocicleta;
    }

    @Override
    public STMotocicleta guardar(STMotocicleta motocicleta) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(motocicleta);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar motocicleta: " + e.getMessage());
        }
        return motocicleta;
    }

    @Override
    public STMotocicleta actualizar(STMotocicleta motocicleta) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            motocicleta = session.merge(motocicleta);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar motocicleta: " + e.getMessage());
        }
        return motocicleta;
    }

    @Override
    public void eliminar(STMotocicleta motocicleta) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(motocicleta);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar motocicleta: " + e.getMessage());
        }
    }
}
