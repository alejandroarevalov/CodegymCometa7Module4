package lesson15.mappedsuperclass.dao;

import entities.mappedsuperclass.MSMotocicleta;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MSMotocicletaDAOImpl extends AbstractDAO implements MSMotocicletaDAO {

    @Override
    public List<MSMotocicleta> buscarTodos(int offset, int limit) {
        List<MSMotocicleta> motocicletas = null;
        try(Session session = sessionFactory.openSession()) {
            Query<MSMotocicleta> query = session.createQuery("FROM MSMotocicleta", MSMotocicleta.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            motocicletas = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de motocicletas: " + e.getMessage());
        }
        return motocicletas;
    }

    @Override
    public MSMotocicleta buscarPorId(int id) {
        MSMotocicleta motocicleta = null;
        try(Session session = sessionFactory.openSession()) {
            motocicleta = session.get(MSMotocicleta.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar la motocicleta: " + e.getMessage());
        }
        return motocicleta;
    }

    @Override
    public MSMotocicleta guardar(MSMotocicleta motocicleta) {
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
    public MSMotocicleta actualizar(MSMotocicleta motocicleta) {
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
    public void eliminar(MSMotocicleta motocicleta) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(motocicleta);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar motocicleta: " + e.getMessage());
        }
    }
}
