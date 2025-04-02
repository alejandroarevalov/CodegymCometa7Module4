package lesson12.dao;

import entities.Documento;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DocumentoDAOImpl extends AbstractDAO implements GenericDAO<Documento> {
    @Override
    public List<Documento> buscarTodos(int offset, int limit) {
        throw new UnsupportedOperationException("No implementado aun...");
    }

    @Override
    public Documento buscarPorId(int id) {
        Documento documento = null;
        try (Session session = sessionFactory.openSession()) {
            documento = session.find(Documento.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el documento: " + e.getMessage());
        }
        return documento;
    }

    @Override
    public Documento guardar(Documento documento) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(documento);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar el documento: " + e.getMessage());
        }
        return documento;
    }

    @Override
    public Documento actualizar(Documento entidad) {
        throw new UnsupportedOperationException("No implementado aun...");
    }

    @Override
    public void eliminar(Documento entidad) {
        throw new UnsupportedOperationException("No implementado aun...");
    }
}
