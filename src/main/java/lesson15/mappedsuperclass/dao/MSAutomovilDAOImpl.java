package lesson15.mappedsuperclass.dao;

import entities.mappedsuperclass.MSAutomovil;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MSAutomovilDAOImpl extends AbstractDAO implements MSAutomovilDAO {

    @Override
    public List<MSAutomovil> buscarTodos(int offset, int limit) {
        List<MSAutomovil> automoviles = null;
        try(Session session = sessionFactory.openSession()) {
            Query<MSAutomovil> query = session.createQuery("FROM MSAutomovil", MSAutomovil.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            automoviles = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de automóviles" + e.getMessage());
        }
        return automoviles;
    }

    @Override
    public MSAutomovil buscarPorId(int id) {
        MSAutomovil automovil = null;
        try(Session session = sessionFactory.openSession()) {
            automovil = session.get(MSAutomovil.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el automóvil: " + e.getMessage());
        } return automovil;
    }

    @Override
    public MSAutomovil guardar(MSAutomovil automovil) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(automovil);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar autómovil: " + e.getMessage());
        }
        return automovil;
    }

    @Override
    public MSAutomovil actualizar(MSAutomovil automovil) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            automovil = session.merge(automovil);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar autómovil: " + e.getMessage());
        }
        return automovil;
    }

    @Override
    public void eliminar(MSAutomovil automovil) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(automovil);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar autómovil: " + e.getMessage());
        }
    }
}
