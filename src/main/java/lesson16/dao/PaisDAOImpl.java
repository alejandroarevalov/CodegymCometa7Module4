package lesson16.dao;

import entities.Pais;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lesson16.generic.AbstractDAO;
import org.hibernate.Session;

public class PaisDAOImpl extends AbstractDAO implements PaisDAO {

    @Override
    public Pais buscarPorNombre(String nombre) {
        Pais pais = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Pais> query = builder.createQuery(Pais.class);
            Root<Pais> root = query.from(Pais.class);
            Predicate condicionNombre = builder.equal(root.get("nombre"), nombre);
            query.select(root).where(condicionNombre);
            pais = session.createQuery(query).getSingleResult();
        } catch (RuntimeException e) {
            String mensajeError = String.format("Error al recuperar el país con nombre %s : %s", nombre, e.getMessage());
            System.err.println(mensajeError);
        }
        return pais;
    }

}
