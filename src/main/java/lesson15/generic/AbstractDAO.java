package lesson15.generic;

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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractDAO {

    protected SessionFactory sessionFactory;

    public AbstractDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(MSAutomovil.class)
            .addAnnotatedClass(MSMotocicleta.class)
            .addAnnotatedClass(MSAvion.class)
            .addAnnotatedClass(STVehiculo.class)
            .addAnnotatedClass(STAutomovil.class)
            .addAnnotatedClass(STMotocicleta.class)
            .addAnnotatedClass(STAvion.class)
            .addAnnotatedClass(JTVehiculo.class)
            .addAnnotatedClass(JTAutomovil.class)
            .addAnnotatedClass(JTMotocicleta.class)
            .addAnnotatedClass(JTAvion.class)
            .addPackage("entities")
            .buildSessionFactory();
    }
}
