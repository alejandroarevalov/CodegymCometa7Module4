package lesson11;

import entities.Estudiante;
import entities.Pais;
import lesson11.dao.EstudianteDAO;
import lesson11.dao.EstudianteDAOImpl;
import lesson11.dao.PaisDAO;
import lesson11.dao.PaisDAOImpl;

import java.time.LocalDate;
import java.util.List;

public class Lesson11Main {

    private EstudianteDAO estudianteDAO;
    private PaisDAO paisDAO;

    public Lesson11Main(EstudianteDAO estudianteDAO, PaisDAO paisDAO) {
        this.estudianteDAO = estudianteDAO;
        this.paisDAO = paisDAO;
    }

    public Estudiante guardarEstudiante() {
        Estudiante nuevoEstudiante = Estudiante.builder()
            .conNombre("Juliana")
            .conApellido("Samper")
            .conNumeroContacto("+572114834")
            .conFechaNacimiento(LocalDate.of(1987, 7, 20))
            .conActivo(1)
            .build();
        estudianteDAO.guardar(nuevoEstudiante);
        System.out.println("Estudiante guardado exitosamente: " + nuevoEstudiante);
        return nuevoEstudiante;
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante) {
        Pais pais = paisDAO.buscarPorNombre("Colombia");
        estudiante.setPais(pais);
        estudianteDAO.actualizar(estudiante);
        System.out.println("Estudiante actualizado exitosamente: " + estudiante);
        return estudiante;
    }

    public void buscarPaisPorId(int id) {
        Pais pais = paisDAO.buscarPorId(id);
        System.out.println("Pais recuperado: " + pais);
    }

    public void sincronizarEstudiante(Estudiante estudiante) {
        estudianteDAO.sincronizar(estudiante);
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        estudianteDAO.eliminar(estudiante);
        System.out.println("Estudiante eliminado: " + estudiante);
    }

    public void eliminarEstudianteConHQL(int id) {
        estudianteDAO.eliminarConHQL(id);
        System.out.println("Estudiante eliminado con ID: " + id);
    }

    public void eliminarEstudianteConQueryNativa(int id) {
        estudianteDAO.eliminarConNativeQuery(id);
        System.out.println("Estudiante eliminado con ID: " + id);
    }

    public void eliminarEstudianteSuave() {
        Estudiante x = estudianteDAO.buscarEstudiantePorId(59);
        estudianteDAO.eliminarSuave(x);
        List<Estudiante> todos = estudianteDAO.buscarTodos();
        todos.forEach(System.out::println);
    }

    public static void main(String[] args) {
        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        PaisDAO paisDAO = new PaisDAOImpl();
        Lesson11Main lesson11Main = new Lesson11Main(estudianteDAO, paisDAO);
        String dash = "-";
        int dashRepetitions = 140;

        Estudiante estudiante = lesson11Main.guardarEstudiante();
        System.out.println(dash.repeat(dashRepetitions));
        estudiante = lesson11Main.actualizarEstudiante(estudiante);
        System.out.println(dash.repeat(dashRepetitions));
        lesson11Main.buscarPaisPorId(555);
        System.out.println(dash.repeat(dashRepetitions));
        lesson11Main.sincronizarEstudiante(estudiante);
        System.out.println(dash.repeat(dashRepetitions));
        lesson11Main.eliminarEstudiante(estudiante);
        System.out.println(dash.repeat(dashRepetitions));
        lesson11Main.eliminarEstudianteConHQL(200);
        System.out.println(dash.repeat(dashRepetitions));
        lesson11Main.eliminarEstudianteConQueryNativa(202);
        lesson11Main.eliminarEstudianteSuave();
    }
}
