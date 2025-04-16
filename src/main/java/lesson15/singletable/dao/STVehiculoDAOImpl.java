package lesson15.singletable.dao;

import entities.singletable.STVehiculo;
import lesson15.generic.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class STVehiculoDAOImpl extends AbstractDAO implements STVehiculoDAO {

    @Override
    public List<STVehiculo> buscarTodos(int offset, int limit) {
        List<STVehiculo> vehiculos = null;
        try (Session session = sessionFactory.openSession()) {
            Query<STVehiculo> query = session.createQuery("FROM STVehiculo", STVehiculo.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            vehiculos = query.getResultList();
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar lista de vehículos: " + e.getMessage());
        }
        return vehiculos;
    }

    @Override
    public STVehiculo buscarPorId(int id) {
        STVehiculo vehiculo = null;
        try (Session session = sessionFactory.openSession()) {
            vehiculo = session.get(STVehiculo.class, id);
        } catch (RuntimeException e) {
            System.err.println("Error al recuperar el vehículo: " + e.getMessage());
        }
        return vehiculo;
    }

    @Override
    public STVehiculo guardar(STVehiculo vehiculo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(vehiculo);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al guardar vehículo: " + e.getMessage());
        }
        return vehiculo;
    }

    @Override
    public STVehiculo actualizar(STVehiculo vehiculo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            vehiculo = session.merge(vehiculo);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
        }
        return vehiculo;
    }

    @Override
    public void eliminar(STVehiculo vehiculo) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(vehiculo);
            transaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }
}

