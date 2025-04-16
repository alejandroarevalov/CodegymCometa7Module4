package lesson14.dao;

import entities.Estudiante;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EstudianteDAOImpl extends AbstractDAO implements EstudianteDAO {

    @Override
    public List<Estudiante> buscarTodos(int offset, int limit) {
        List<Estudiante> estudiantes = null;
        try(Session session = sessionFactory.openSession()) {
            Query<Estudiante> query = session.createQuery("from Estudiante", Estudiante.class);
            if (offset > 0) {
                query.setFirstResult(offset);
            }
            if (limit > 0) {
                query.setMaxResults(limit);
            }
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar estudiantes: " + e.getMessage());
        }
        return estudiantes;
    }

    @Override
    public Estudiante buscarPorId(int id) {
        Estudiante estudiante = null;
        try (Session session = sessionFactory.openSession()) {
            estudiante = session.find(Estudiante.class, id);
            estudiante.getLibros().get(0); // -> Para activar la carga perezosa/tardía
            // Hibernate.size(estudiante.getLibros());
            System.out.println(estudiante);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public Estudiante buscarPorIdUsandoFetchJoin(int id) {
        Estudiante estudiante = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Estudiante> estudianteQuery = session.createQuery(
                "FROM Estudiante e JOIN FETCH libros l WHERE e.id = :id", Estudiante.class);
            estudianteQuery.setParameter("id", id);
            estudianteQuery.setFirstResult(0);
            estudianteQuery.setMaxResults(1);
            estudiante = estudianteQuery.getSingleResult();
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public Estudiante buscarPorIdMostrandoCacheDePrimerNivel(int id) {
        Estudiante estudiante1 = null;
        try (Session session = sessionFactory.openSession()) {
            session.setCacheMode(CacheMode.NORMAL);
            estudiante1 = session.get(Estudiante.class, id);
            Estudiante estudiante2 = session.get(Estudiante.class, id);
            System.out.println("Son iguales?: " + estudiante1.equals(estudiante2));
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudiante1;
    }

    // TODO: Usar este método para explicar caché de segundo nivel
    public Estudiante buscarPorIdUsando2SesionesConCacheDeSegundoNivel(int id) {
        Estudiante estudiante1 = null;
        try (Session session = sessionFactory.openSession()) {
            estudiante1 = session.get(Estudiante.class, id);
            System.out.println("Estudiante 1: " + estudiante1);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        Estudiante estudiante2 = null;
        System.out.println("Estudiante en caché?: " + sessionFactory.getCache().containsEntity(Estudiante.class, id));
        try (Session session = sessionFactory.openSession()) {
            estudiante2 = session.get(Estudiante.class, id);
            System.out.println("Estudiante 2: " + estudiante2);
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return estudiante1 != null ? estudiante1 : estudiante2;
    }

    public Estudiante buscarPorIdUsando2SesionesConCacheDeConsultas(int id) {
        System.out.println("Estudiante " + id + " en caché?: " +
            sessionFactory.getCache().containsEntity(Estudiante.class, id)
        );
        Estudiante estudiante1 = null;
        try (Session session = sessionFactory.openSession()) {
            Query<Estudiante> query1 = session.createQuery("FROM Estudiante WHERE id = :id", Estudiante.class);
            query1.setCacheable(true).setCacheMode(CacheMode.NORMAL);
            query1.setParameter("id", id);
            estudiante1 = query1.getSingleResult();
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        Estudiante estudiante2 = null;
        System.out.println("Estudiante " + id + " en caché?: " +
            sessionFactory.getCache().containsEntity(Estudiante.class, id)
        );
        try (Session session = sessionFactory.openSession()) {
            Query<Estudiante> query2 = session.createQuery("FROM Estudiante WHERE id = :id", Estudiante.class);
            query2.setCacheable(true).setCacheMode(CacheMode.NORMAL);
            query2.setParameter("id", id);
            estudiante2 = query2.getSingleResult();
        } catch (RuntimeException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        System.out.println(
            "Recuperaciones de la caché de consultas: " + sessionFactory.getStatistics().getQueryCacheHitCount()
        );
        return estudiante2;
    }

    @Override
    public void borrarEstudianteDeCache(int id) {
        boolean estabaEnCacheAntes = sessionFactory.getCache().containsEntity(Estudiante.class, id);
        System.out.println("¿Estudiante estaba en caché? " + estabaEnCacheAntes);

        Estudiante estudiante = null;
        try (Session session = sessionFactory.openSession()) {
            estudiante = session.get(Estudiante.class, id);
            System.out.println("Estudiante recuperado: " + estudiante);
        }

        boolean estaEnCache = sessionFactory.getCache().containsEntity(Estudiante.class, id);
        System.out.println("¿Estudiante está en caché después de recuperarlo? " + estaEnCache);

        sessionFactory.getCache().evictEntityData(Estudiante.class, id);
        System.out.println("Estudiante eliminado de la caché");

        boolean estaEnCacheFinal = sessionFactory.getCache().containsEntity(Estudiante.class, id);
        System.out.println("¿Estudiante sigue en caché? " + estaEnCacheFinal);
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public Estudiante actualizar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            estudiante = session.merge(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
        }
        return estudiante;
    }

    @Override
    public void eliminar(Estudiante estudiante) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(estudiante);
            tx.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}
