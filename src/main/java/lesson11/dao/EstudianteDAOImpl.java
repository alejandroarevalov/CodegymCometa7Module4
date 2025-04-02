package lesson11.dao;

import entities.Estudiante;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;

import java.util.List;

public class EstudianteDAOImpl extends AbstractDAO implements EstudianteDAO {

    @Override
    public Estudiante buscarEstudiantePorId(int id) {
        Estudiante estudiante = null;
        try(Session session = sessionFactory.openSession()) {
            estudiante = session.find(Estudiante.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al obtener el estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public List<Estudiante> buscarTodos() {
        try(Session session = sessionFactory.openSession()) {
            session.enableFilter("estudiantesActivos").setParameter("activo", 1);
            Query<Estudiante> query = session.createQuery("from Estudiante", Estudiante.class);
            return query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al obtener todos los estudiantes: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void guardar(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(estudiante);
            estudiante.setNumeroContacto("+00123456123456");
            session.flush(); // Establecer un punto de transacción parcialmente completa.
            session.evict(estudiante); // Desvincular del contexto de persistencia.
            estudiante.setNumeroContacto("+99999999999999");
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar el estudiante: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(estudiante); // metodos update() y saveOrUpdate() deprecados
            tx.commit();
        }
    }

    @Override
    public void sincronizar(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Estudiante administrado = session.merge(estudiante);
            System.out.println("Estado actual: " + administrado);
            simularCambioEnBaseDeDatos(administrado);
            session.refresh(administrado);
            System.out.println("Estado al refrescar: " + administrado);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al sincronizar estudiante: " + e.getMessage());
        }
    }

    private void simularCambioEnBaseDeDatos(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Estudiante administrado = session.merge(estudiante);
            administrado.setNumeroContacto("+22333333333");
            tx.commit();
            System.out.println("Cambio simulado en nueva sesión exitoso.");
        } catch (RuntimeException e) {
            System.err.println("No fue posible simular cambio en el estudiante: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Estudiante estudiante) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }

    @Override
    public void eliminarConHQL(int id) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            MutationQuery query = session.createMutationQuery("DELETE FROM Estudiante WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }

    @Override
    public void eliminarConNativeQuery(int id) {
        try (Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            MutationQuery queryNativa = session.createNativeQuery("DELETE FROM estudiantes WHERE id = :id");
            queryNativa.setParameter("id", id);
            queryNativa.executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }

    public void eliminarSuave(Estudiante estudiante) {
        try(Session session = this.sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            estudiante.setActivo(false);
            session.merge(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}
