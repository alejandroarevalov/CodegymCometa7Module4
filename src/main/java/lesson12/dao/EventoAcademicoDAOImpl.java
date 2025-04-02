package lesson12.dao;

import entities.EventoAcademico;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EventoAcademicoDAOImpl extends AbstractDAO implements GenericDAO<EventoAcademico> {

    @Override
    public List<EventoAcademico> buscarTodos(int offset, int limit) {
        List<EventoAcademico> eventosAcademicos = null;
        try(Session session = sessionFactory.openSession()) {
            Query<EventoAcademico> query = session.createQuery("from EventoAcademico ", EventoAcademico.class);
            if (offset > 0) {
                query.setFirstResult(offset);
            }
            if (limit > 0) {
                query.setMaxResults(limit);
            }
            eventosAcademicos = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar eventos academicos: " + e.getMessage());
        }
        return eventosAcademicos;
    }

    @Override
    public EventoAcademico buscarPorId(int id) {
        EventoAcademico eventoAcademico = null;
        try (Session session = sessionFactory.openSession()) {
            eventoAcademico = session.find(EventoAcademico.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar evento academico: " + e.getMessage());
        }
        return eventoAcademico;
    }

    @Override
    public EventoAcademico guardar(EventoAcademico eventoAcademico) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(eventoAcademico);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al agregar evento academico: " + e.getMessage());
        }
        return eventoAcademico;
    }

    @Override
    public EventoAcademico actualizar(EventoAcademico eventoAcademico) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            eventoAcademico = session.merge(eventoAcademico);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar evento academico: " + e.getMessage());
        }
        return eventoAcademico;
    }

    @Override
    public void eliminar(EventoAcademico eventoAcademico) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(eventoAcademico);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}
