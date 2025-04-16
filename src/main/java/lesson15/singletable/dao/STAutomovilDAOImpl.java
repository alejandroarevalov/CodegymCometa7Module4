package lesson15.singletable.dao;

import entities.singletable.STAutomovil;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class STAutomovilDAOImpl extends AbstractDAO implements STAutomovilDAO {

    @Override
    public List<STAutomovil> buscarTodos(int offset, int limit) {
        List<STAutomovil> automoviles = null;
        try(Session session = sessionFactory.openSession()) {
            Query<STAutomovil> query = session.createQuery("FROM STAutomovil", STAutomovil.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            automoviles = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de automóviles" + e.getMessage());
        }
        return automoviles;
    }

    @Override
    public STAutomovil buscarPorId(int id) {
        STAutomovil automovil = null;
        try(Session session = sessionFactory.openSession()) {
            automovil = session.get(STAutomovil.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el automóvil: " + e.getMessage());
        } return automovil;
    }

    @Override
    public STAutomovil guardar(STAutomovil automovil) {
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
    public STAutomovil actualizar(STAutomovil automovil) {
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
    public void eliminar(STAutomovil automovil) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(automovil);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar autómovil: " + e.getMessage());
        }
    }
}
