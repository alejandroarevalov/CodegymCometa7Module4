package lesson16.generic;

import entities.ContactoDeEmergencia;
import entities.Curso;
import entities.Estudiante;
import entities.Libro;
import entities.Pais;
import entities.joined.JTAutomovil;
import entities.joined.JTAvion;
import entities.joined.JTMotocicleta;
import entities.joined.JTVehiculo;
import entities.mappedsuperclass.MSAutomovil;
import entities.mappedsuperclass.MSAvion;
import entities.mappedsuperclass.MSMotocicleta;
import entities.singletable.STAutomovil;
import entities.singletable.STAvion;
import entities.singletable.STMotocicleta;
import entities.singletable.STVehiculo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

import static java.lang.String.format;

public abstract class AbstractDAO implements GenericDAO {

    protected SessionFactory sessionFactory;

    public AbstractDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addAnnotatedClass(ContactoDeEmergencia.class)
            .addAnnotatedClass(Pais.class)
            .addAnnotatedClass(Curso.class)
            .addAnnotatedClass(Libro.class)
            .addPackage("entities")
            .buildSessionFactory();
    }

    @Override
    public <T> List<T> buscarTodosConTipo(Class<T> tipo, int offset, int limit) {
        List<T> resultados = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tipo);
            Root<T> root = criteriaQuery.from(tipo);
            criteriaQuery.select(root);
            resultados = session.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al buscar los registros de tipo %s: %s", tipo.getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultados;
    }

    @Override
    public <T> T buscarPorIdConTipo(Class<T> tipo, int id) {
        T resultado = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tipo);
            Root<T> root = criteriaQuery.from(tipo);
            Predicate condicionIdsIguales = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.select(root).where(condicionIdsIguales);
            resultado = session.createQuery(criteriaQuery).getSingleResult();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al buscar el registro del tipo %s con id %d: %s",
                tipo.getSimpleName(), id, e.getMessage());
            System.err.println(mensajeError);
        }
        return resultado;
    }

    @Override
    public <T> Long contarConTipo(Class<T> tipo) {
        Long resultado = -1L;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<T> root = criteriaQuery.from(tipo);
            criteriaQuery.select(criteriaBuilder.count(root));
            resultado = session.createQuery(criteriaQuery).getSingleResult();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al contar registros del tipo %s: %s",
                tipo.getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultado;
    }

    @Override
    public <T> T guardar(T entidad) {
        // Nota: No se puede usar Criteria API para insertar entidades nuevas
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al guardar nueva entidad del tipo %s: %s",
                entidad.getClass().getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
        return entidad;
    }

    @Override
    public <T> T actualizar(T entidad) {
        T resultado = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            resultado = session.merge(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al actualizar entidad del tipo %s: %s",
                entidad.getClass().getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
        return resultado;
    }

    @Override
    public <T> void eliminar(T entidad) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(entidad);
            tx.commit();
        } catch (RuntimeException e) {
            String mensajeError = format("Error al eliminar entidad del tipo %s: %s",
                entidad.getClass().getSimpleName(), e.getMessage());
            System.err.println(mensajeError);
        }
    }
}
