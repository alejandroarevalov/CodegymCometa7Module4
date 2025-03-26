package lesson10;

import lesson10.dao.EstudianteDAO;
import lesson10.entities.Estudiante;

import java.time.LocalDate;
import java.util.List;

public class Lesson10Main {

    private final EstudianteDAO estudianteDAO;

    public Lesson10Main() {
        estudianteDAO = new EstudianteDAO();
    }

    public void obtenerEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.obtenerTodos();
        estudiantes.forEach(System.out::println);
    }

    public void obtenerFechasDeNacimientoDistintas() {
        List<LocalDate> fechas = estudianteDAO.obtenerFechasDeNacimientoDistintas();
        fechas.forEach(System.out::println);
    }

    public void obtenerNombresCompletos() {
        List<String> nombres = estudianteDAO.obtenerNombresCompletos();
        nombres.forEach(System.out::println);
    }

    public void obtenerNombresCompletosConFechaDeNacimiento() {
        List<Object[]> nombresYFechas = estudianteDAO.obtenerNombresCompletosConFechaDeNacimiento();
        for (Object[] fila : nombresYFechas) {
            System.out.println("Nombre: " + fila[0] + " Fecha: " + fila[1]);
        }
    }

    public void obtenerTodosUsandoTypedQuery() {
        List<Estudiante> estudiantes = estudianteDAO.obtenerTodosUsandoTypedQuery();
        estudiantes.forEach(System.out::println);
    }

    public void obtenerEstudiantesFiltrandoConStreams() {
        List<Estudiante> estudiantesFiltrados = estudianteDAO.obtenerTodosYFiltrarConStreams();
        estudiantesFiltrados.forEach(System.out::println);
    }

    public void actualizarNumeroContacto(Integer id, String numeroContacto) {
        if (estudianteDAO.actualizarNumeroContacto(id, numeroContacto)) {
            System.out.println("Estudiante con id: " + id + " actualizado correctamente");
            Estudiante actualizado = estudianteDAO.obtenerEstudiante(id);
            System.out.println("Estudiante actualizado: " + actualizado);
        } else {
            System.out.println("Estudiante con id: " + id + " no actualizado");
        }
    }

    public void obtenerEstudiantesEnColeccionParaRecorrer() {
       estudianteDAO.obtenerEstudiantesEnColeccionParaRecorrer();
    }

    public void obtenerEstudiantesPorPais(String pais) {
        estudianteDAO.obtenerEstudiantesPorPais(pais)
            .forEach(System.out::println);
    }

    public void obtenerEstudiantesDeVariosPaises(String... paises) {
        estudianteDAO.obtenerEstudiantesDeVariosPaises(paises)
            .forEach(System.out::println);
    }

    public void obtenerEstudiantesPaginadosYLimiteDeRegistros() {
        estudianteDAO.obtenerEstudiantesConPaginacionYLimiteDeRegistros()
            .forEach(System.out::println);
    }

    public void obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePais() {
        estudianteDAO.obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePais()
            .forEach(System.out::println);
    }

    public void obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePaisIncluyendoNulos() {
        estudianteDAO.obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePaisIncluyendoNulos()
            .forEach(System.out::println);
    }

    public void obtenerEstudianteMayorAgrupadoPorPais() {
        estudianteDAO.obtenerEstudianteMayorAgrupadoPorPais()
            .forEach(res -> {
                System.out.println("Pais: " + res[0] + " Fecha: " + res[1]);
            });
    }

    public void obtenerEstudiantesUsandoNamedQueries() {
        estudianteDAO.obtenerEstudiantesUsandoNamedQueries()
            .forEach(System.out::println);
    }

    public void obtenerEstudiantesUsandoNativeQueries() {
        estudianteDAO.obtenerEstudiantesUsandoNativeQuery("+573134664466")
            .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Lesson10Main lesson10Main = new Lesson10Main();
        String dash = "-";
        int dashRepetitions = 140;
        lesson10Main.obtenerEstudiantes();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerFechasDeNacimientoDistintas();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerNombresCompletos();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerNombresCompletosConFechaDeNacimiento();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerTodosUsandoTypedQuery();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesFiltrandoConStreams();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.actualizarNumeroContacto(6, "+12345678920");
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesEnColeccionParaRecorrer();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesPorPais("Mexico");
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesDeVariosPaises("Mexico", "Japan", "Peru");
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesPaginadosYLimiteDeRegistros();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePais();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesOrdenadosPorFechaDeNacimientoYNombreDePaisIncluyendoNulos();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudianteMayorAgrupadoPorPais();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesUsandoNamedQueries();
        System.out.println(dash.repeat(dashRepetitions));
        lesson10Main.obtenerEstudiantesUsandoNativeQueries();
    }
}
