package lesson17.dao;

import lesson17.generic.GenericDAO;
import org.hibernate.SessionFactory;

public interface CuentaDAO extends GenericDAO {

    SessionFactory getSessionFactory();
}
