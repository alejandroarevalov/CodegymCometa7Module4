package lesson11.dao;

import entities.Estudiante;
import entities.Pais;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractDAO {

    protected SessionFactory sessionFactory;

    AbstractDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addAnnotatedClass(Pais.class)
            .addPackage("entities")
            .buildSessionFactory();
    }
}
