package lesson9;

import lesson9.dao.EstudianteDAO;
import lesson9.entities.Estudiante;

import java.time.LocalDate;
import java.util.List;

public class Lesson9Main {

   private final EstudianteDAO estudianteDAO;

    public Lesson9Main() {
        estudianteDAO = new EstudianteDAO();
    }

    public void ejemploObtenerEstudiantes() {
        List<Estudiante> todos = estudianteDAO.obtenerTodos();
        todos.forEach(System.out::println);
    }

    public void ejemploObtenerEstudiantePorId(Integer id) {
        Estudiante estudiante = estudianteDAO.obtenerPorId(id);
        System.out.println(estudiante);
    }

    public void ejemploAgregarEstudiante() {
        Estudiante nuevoEstudiante = new Estudiante();
        nuevoEstudiante.setNombre("Eduardo");
        nuevoEstudiante.setApellido("Giraldo");
        nuevoEstudiante.setFechaNacimiento(LocalDate.of(1985, 11, 26));
        nuevoEstudiante.setNumeroContacto("+212221111");
        if(estudianteDAO.agregarEstudiante(nuevoEstudiante)) {
            System.out.println("Estudiante agregado correctamente: " + nuevoEstudiante);
        } else {
            System.out.println("Estudiante no agregado");
        }
    }

    public void ejemploObtenerEstudiantePorNombre(String nombre) {
        List<Estudiante> estudiantesEncontrados = estudianteDAO.obtenerPorNombre(nombre);
        estudiantesEncontrados.forEach(System.out::println);
    }

    public void ejemploActualizarEstudiante() {
        Estudiante estudianteAActualizar = estudianteDAO.obtenerPorNombre("Eduardo").get(0);
        estudianteAActualizar.setNumeroContacto("+25333333333");
        if(estudianteDAO.actualizarEstudiante(estudianteAActualizar)) {
            System.out.println("Estudiante actualizado correctamente: " + estudianteAActualizar);
        } else {
            System.out.println("Estudiante no actualizado");
        }
    }

    public void ejemploEliminarEstudiante() {
        Estudiante estudianteAEliminar = estudianteDAO.obtenerPorNombre("Eduardo").get(0);
        if(estudianteDAO.eliminarEstudiante(estudianteAEliminar)) {
            System.out.println("Estudiante eliminado correctamente: " + estudianteAEliminar);
        } else {
            System.out.println("Estudiante no eliminado");
        }
    }
    public static void main(String[] args) {

        Lesson9Main lesson9Main = new Lesson9Main();
        lesson9Main.ejemploObtenerEstudiantes();
        System.out.println("---------------------------------------------------------------");
        lesson9Main.ejemploObtenerEstudiantePorId(8);
        System.out.println("---------------------------------------------------------------");
        lesson9Main.ejemploAgregarEstudiante();
        System.out.println("---------------------------------------------------------------");
        lesson9Main.ejemploObtenerEstudiantePorNombre("Eduardo");
        System.out.println("---------------------------------------------------------------");
        lesson9Main.ejemploActualizarEstudiante();
        System.out.println("---------------------------------------------------------------");
        lesson9Main.ejemploEliminarEstudiante();
    }
}
