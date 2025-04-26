package lesson17.dao;

import lesson17.generic.AbstractDAO;
import org.hibernate.SessionFactory;

public class CuentaDAOImpl extends AbstractDAO implements CuentaDAO {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
