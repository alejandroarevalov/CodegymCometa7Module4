package lesson13;

import entities.Estudiante;
import entities.Pais;
import lesson13.dao.EstudianteDAOImpl;
import lesson13.dao.GenericDAO;
import lesson13.dao.PaisDAO;
import lesson13.dao.PaisDAOImpl;

import java.util.List;

public class Lesson13Main {

    private final GenericDAO<Estudiante> estudianteDAO;
    private final PaisDAO paisDAO;

    public Lesson13Main(GenericDAO<Estudiante> estudianteDAO, PaisDAO paisDAO) {
        this.estudianteDAO = estudianteDAO;
        this.paisDAO = paisDAO;
    }

    public void buscarTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.buscarTodos(0, 10);
        estudiantes.forEach(System.out::println);
    }

    public void agregarNuevoIdioma() {
        Estudiante estudiante = estudianteDAO.buscarPorId(8);
        estudiante.getIdiomas().add("Japonés");
        estudianteDAO.actualizar(estudiante);
        System.out.println("Nuevo idioma agregado con exito al estudiante " + estudiante.getNombreCompleto());
    }

    public void buscarPaisesAsociadosAEstudiantes() {
        List<Pais> paisesConEstudiantes = paisDAO.buscarPaisesConEstudiantes();
        paisesConEstudiantes.forEach(System.out::println);
    }

    public static void main(String[] args) {
        GenericDAO<Estudiante> estudianteDAO = new EstudianteDAOImpl();
        PaisDAO paisDAO = new PaisDAOImpl();
        Lesson13Main launcher = new Lesson13Main(estudianteDAO, paisDAO);
        String dash = "-";
        int dashRepetitions = 140;
        launcher.buscarTodosLosEstudiantes();
        System.out.println(dash.repeat(dashRepetitions));
        launcher.agregarNuevoIdioma();
        System.out.println(dash.repeat(dashRepetitions));
        launcher.buscarPaisesAsociadosAEstudiantes();


    }
}
