package lesson13.dao;

import entities.Estudiante;
import entities.Pais;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaisDAOImpl extends AbstractDAO implements PaisDAO {
    @Override
    public List<Pais> buscarTodos(int offset, int limit) {
        List<Pais> paises = null;
        try(Session session = sessionFactory.openSession()) {
            Query<Pais> query = session.createQuery("from Pais", Pais.class);
            if (offset > 0) {
                query.setFirstResult(offset);
            }
            if (limit > 0) {
                query.setMaxResults(limit);
            }
            paises = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar paises: " + e.getMessage());
        }
        return paises;
    }

    @Override
    public Pais buscarPorId(int id) {
        Pais pais = null;
        try (Session session = sessionFactory.openSession()) {
            pais = session.find(Pais.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar pais: " + e.getMessage());
        }
        return pais;
    }

    @Override
    public Pais guardar(Pais pais) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(pais);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al agregar pais: " + e.getMessage());
        }
        return pais;
    }

    @Override
    public Pais actualizar(Pais pais) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            pais = session.merge(pais);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar pais: " + e.getMessage());
        }
        return pais;
    }

    @Override
    public void eliminar(Pais pais) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(pais);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar pais: " + e.getMessage());
        }
    }

    public List<Pais> buscarPaisesConEstudiantes() {
        List<Pais> paises = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Pais> query = session.createQuery("FROM Pais p WHERE p.estudiantes IS NOT EMPTY", Pais.class);
            paises = query.getResultList();
            paises.forEach(p -> p.getEstudiantes().size()); // Forzar la carga de estudiantes por pais sin FetchType.EAGER.
        } catch (RuntimeException e) {
            System.err.println("Error al buscar paises: " + e.getMessage());
        }
        return paises;
    }
}
