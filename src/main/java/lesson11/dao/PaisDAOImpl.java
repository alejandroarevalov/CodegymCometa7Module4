package lesson11.dao;

import entities.Pais;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PaisDAOImpl extends AbstractDAO implements PaisDAO {

    @Override
    public Pais buscarPorId(Integer id) {
        Pais pais = null;
        try(Session session = this.sessionFactory.openSession()) {
            pais = session.find(Pais.class, id); // Igual que session.get()
            // pais = session.getReference(Pais.class, id); // session.load() está deprecado.
        } catch (RuntimeException e) {
            System.err.println("Error al buscar pais por id: " + id + ": " + e.getMessage());
        }
        return pais;
    }

    @Override
    public Pais buscarPorNombre(String nombre) {
        Pais pais;
        try(Session session = sessionFactory.openSession()) {
            Query<Pais> query = session.createQuery(
                "FROM Pais WHERE nombre = :nombre", Pais.class);
            query.setParameter("nombre", nombre);
            pais = query.getSingleResult();
        }
        return pais;
    }
}
