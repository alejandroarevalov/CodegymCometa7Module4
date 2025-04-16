package lesson10.dao;

import entities.Estudiante;
import entities.Pais;
import jakarta.persistence.TypedQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteDAO {

    private final SessionFactory sessionFactory;

    public EstudianteDAO() {
        this.sessionFactory = new Configuration()
            .addAnnotatedClass(Estudiante.class)
            .addAnnotatedClass(Pais.class)
            .addPackage("entities")
            .buildSessionFactory();
    }

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            estudiantes = session.createQuery("from Estudiante", Estudiante.class).list();
        }
        return estudiantes;
    }

    public List<LocalDate> obtenerFechasDeNacimientoDistintas() {
        List<LocalDate> fechas;
        try (Session session = this.sessionFactory.openSession()) {
            Query<LocalDate> fechasQuery = session.createQuery(
                "select distinct e.fechaNacimiento from Estudiante e", LocalDate.class
            );
            fechas = fechasQuery.getResultList();
        }
        return fechas;
    }

    public List<String> obtenerNombresCompletos() {
        List<String> nombresCompletos;
        try (Session session = this.sessionFactory.openSession()) {
            Query<String> nombresQuery = session.createQuery(
                "select concat(nombreCompleto.nombre, ' ', nombreCompleto.apellido) from Estudiante", String.class
            );
            nombresCompletos = nombresQuery.getResultList();
        }
        return nombresCompletos;
    }

    // Query vs TypedQuery
    public List<Object[]> obtenerNombresCompletosConFechaDeNacimiento() {
        List<Object[]> nombresYFechas;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Object[]> consulta = session.createQuery(
                "select concat(e.nombreCompleto.nombre, ' ', e.nombreCompleto.apellido) as nombreCompleto, e.fechaNacimiento" +
                    " from Estudiante e", Object[].class
            );
            nombresYFechas = consulta.getResultList();
        }
        return nombresYFechas;
    }

    // Query vs TypedQuery
    public List<Estudiante> obtenerTodosUsandoTypedQuery() {
        List<Estudiante> estudiantes;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Estudiante> estudiantesTypedQuery = session.createQuery("FROM Estudiante e", Estudiante.class);
            estudiantes = estudiantesTypedQuery.getResultList();
        }
        return estudiantes;
    }

    // ResultStream
    public List<Estudiante> obtenerTodosYFiltrarConStreams() {
        List<Estudiante> estudiantes;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<Estudiante> estudiantesTypedQuery = session.createQuery("FROM Estudiante e", Estudiante.class);
            estudiantes = estudiantesTypedQuery.getResultStream()
                .filter(e -> e.getFechaNacimiento().isBefore(
                    LocalDate.of(2006, 2, 15))
                ).collect(Collectors.toList());
        }
        return estudiantes;
    }

    // Métodos createQuery vs createMutationQuery
    public boolean actualizarNumeroContacto(Integer id, String numeroContacto) {
        try (Session session = this.sessionFactory.openSession()) {
            session.setDefaultReadOnly(false);
            Transaction tx = session.beginTransaction();
            var query = session.createMutationQuery(
                "update Estudiante set numeroContacto = :numeroContacto where id = :id"
            );
            query.setParameter("numeroContacto", numeroContacto);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Actualización no realizada: " + e.getMessage());
            return false;
        }
    }

    public Estudiante obtenerEstudiante(Integer id) {
        Estudiante estudiante;
        try (Session session = this.sessionFactory.openSession()) {
            estudiante = session.get(Estudiante.class, id);
        }
        return estudiante;
    }

    public void obtenerEstudiantesEnColeccionParaRecorrer() {
        ScrollableResults<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> estudianteQuery = session.createQuery("FROM Estudiante", Estudiante.class);
            ScrollableResults<Estudiante> estudiantesScrollable = estudianteQuery
                .scroll(ScrollMode.SCROLL_INSENSITIVE);
            // Ir al primer estudiante
            if (estudiantesScrollable.first()) {
                System.out.println("Primer estudiante: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Ir al último estudiante
            if (estudiantesScrollable.last()) {
                System.out.println("Último estudiante: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Obtener la posición actual
            System.out.println("Posición actual: " + estudiantesScrollable.getRowNumber());

            // Retroceder al estudiante anterior
            if (estudiantesScrollable.previous()) {
                System.out.println("Estudiante anterior: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Avanzar al siguiente estudiante
            if (estudiantesScrollable.next()) {
                System.out.println("Estudiante siguiente: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Moverse a una posición específica
            if (estudiantesScrollable.scroll(-10)) { // Avanza o retrocede n posiciones desde la actual
                System.out.println("Después de retroceder 10 posiciones: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Usar position() para moverse a un índice específico (ejemplo: índice 3)
            if (estudiantesScrollable.position(3)) {
                System.out.println("Estudiante en posición 3: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            }

            // Obtener la fila actual
            System.out.println("Número de fila actual: " + estudiantesScrollable.getRowNumber());

            // Establecer una nueva posición en la fila (ejemplo: volver a la fila 1)
            estudiantesScrollable.setRowNumber(1);
            System.out.println("Volviendo a fila 1: " + estudiantesScrollable.get().getNombreCompleto().getNombre());
            estudiantesScrollable.close();
        }
    }

    public List<Estudiante> obtenerEstudiantesPorPais(String nombreDeUnPais) {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            TypedQuery<Estudiante> estudianteTypedQuery = session.createQuery(
                "FROM Estudiante e WHERE e.pais.nombre = :nombrePais", Estudiante.class);
            estudianteTypedQuery.setParameter("nombrePais", nombreDeUnPais);
            estudiantes = estudianteTypedQuery.getResultList();
        }
        return estudiantes;
    }

    public List<Estudiante> obtenerEstudiantesDeVariosPaises(String ...paises) {
        String[] parametrosPaises = paises;
        List<Estudiante> estudiantes;
        String hql = "FROM Estudiante e WHERE e.pais.nombre IN (:paisesParams)";
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> estudianteTypedQuery = session.createQuery(hql, Estudiante.class);
            estudianteTypedQuery.setParameterList("paisesParams", parametrosPaises);
            estudiantes = estudianteTypedQuery.getResultList();
        }
        return estudiantes;
    }

    public List<Estudiante> obtenerEstudiantesConPaginacionYLimiteDeRegistros() {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> query = session.createQuery("FROM Estudiante", Estudiante.class);
            estudiantes = query.setFirstResult(10).setMaxResults(10).getResultList();
        }
        return estudiantes;
    }

    // Trae registros de estudiantes con paises no nulos
    public List<Estudiante> obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePais() {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> query = session.createQuery(
                "FROM Estudiante e ORDER BY e.fechaNacimiento, e.pais.nombre", Estudiante.class
            );
            estudiantes = query.getResultList();
        }
        return estudiantes;
    }

    // Trae registros de estudiantes con paises no nulos
    public List<Estudiante> obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePaisIncluyendoNulos() {
        List<Estudiante> estudiantes;
        String hql = "FROM Estudiante e LEFT JOIN FETCH e.pais ORDER BY e.fechaNacimiento, e.pais.nombre NULLS FIRST";
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> query = session.createQuery(hql, Estudiante.class);
            estudiantes = query.getResultList();
        }
        return estudiantes;
    }

    public List<Object[]> obtenerEstudianteMayorAgrupadoPorPais() {
        List<Object[]> resultados;
        try (Session session = this.sessionFactory.openSession()) {
            String hql = "SELECT e.pais.nombre, " +
                "min(e.fechaNacimiento) AS fechaNacimientoEstudiante " +
                "FROM Estudiante e " +
                "GROUP BY e.pais.nombre";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            resultados = query.getResultList();
        }
        return resultados;
    }

    public List<Estudiante> obtenerEstudiantesUsandoNamedQueries() {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> query = session.createNamedQuery("Estudiante.conNombre", Estudiante.class);
            query.setParameter("patronNombre", "F%");
            estudiantes = query.getResultList();
        }
        return estudiantes;
    }

    public List<Estudiante> obtenerEstudiantesUsandoNativeQuery(String numeroDeContacto) {
        List<Estudiante> estudiantes;
        try (Session session = this.sessionFactory.openSession()) {
            Query<Estudiante> query = session.createNativeQuery(
                "SELECT * FROM Estudiantes WHERE numero_contacto =:numeroContacto", Estudiante.class
            );
            query.setParameter("numeroContacto", numeroDeContacto);
            estudiantes = query.getResultList();
        }
        return estudiantes;
    }
}
