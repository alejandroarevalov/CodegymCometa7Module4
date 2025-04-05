package lesson13.dao;

import entities.ContactoDeEmergencia;
import entities.Curso;
import entities.Documento;
import entities.Estudiante;
import entities.EventoAcademico;
import entities.Pais;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class AbstractDAO {

    protected SessionFactory sessionFactory;

    AbstractDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addAnnotatedClass(EventoAcademico.class)
            .addAnnotatedClass(Pais.class)
            .addAnnotatedClass(Documento.class)
            .addAnnotatedClass(Curso.class)
            .addAnnotatedClass(ContactoDeEmergencia.class)
            .addPackage("entities")
            .buildSessionFactory();
    }
}
