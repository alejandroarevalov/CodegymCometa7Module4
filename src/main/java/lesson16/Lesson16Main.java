package lesson16;

import entities.Estudiante;
import entities.NombreCompleto;
import entities.Pais;
import enums.NivelAcademico;
import lesson16.dao.EstudianteDAO;
import lesson16.dao.EstudianteDAOImpl;
import lesson16.dao.PaisDAO;
import lesson16.dao.PaisDAOImpl;
import lesson16.dto.EstudianteLibroDTO;

import java.time.LocalDate;
import java.util.List;

public class Lesson16Main {

    private final EstudianteDAO estudianteDAO;
    private final PaisDAO paisDAO;

    public Lesson16Main(EstudianteDAO estudianteDAO, PaisDAO paisDAO) {
        this.estudianteDAO = estudianteDAO;
        this.paisDAO = paisDAO;
    }

    public void mostrarTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.buscarTodosConTipo(Estudiante.class, 0, 10);
        estudiantes.forEach(System.out::println);
    }

    public void mostrarEstudiantePorId(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorIdConTipo(Estudiante.class, id);
        System.out.println(estudiante);
    }

    public void mostrarEstudiantesConNivelAcademico(NivelAcademico nivelAcademico) {
        List<Estudiante> estudiantes = estudianteDAO.buscarEstudiantesConNivelAcademico(nivelAcademico);
        estudiantes.forEach(System.out::println);
    }

    public void mostrarEstudiantesConFechaNacimientoAnteriorA(LocalDate fechaNacimiento) {
        List<Estudiante> estudiantes = estudianteDAO.buscarEstudiantesConFechaNacimientoAnteriorA(fechaNacimiento);
        estudiantes.forEach(System.out::println);
    }

    public void mostrarEstudiantesConNombreYNivelAcademico(String nombre, NivelAcademico nivelAcademico) {
        List<Estudiante> estudiantes = estudianteDAO
            .buscarEstudiantesConNombreYNivelAcademico(nombre, nivelAcademico);
        estudiantes.forEach(System.out::println);
    }

    public void contarEstudiantesDePais(String nombrePais) {
        Pais pais = paisDAO.buscarPorNombre(nombrePais);
        long cantidadEstudiantes = estudianteDAO.contarEstudiantesDePais(pais);
        System.out.printf("Cantidad de estudiantes en %s: %d%n\n", nombrePais, cantidadEstudiantes);
    }

    public void desactivarEstudiantesConListaDeIds(List<Long> ids) {
        estudianteDAO.desactivarEstudiantesConListaDeIds(ids);
        List<Estudiante> estudiantes = estudianteDAO.buscarEstudiantesConListaDeIds(ids);
        estudiantes.forEach(System.out::println);
    }

    public void eliminarEstudiantePorNombreCompleto(String nombre, String apellido) {
        NombreCompleto nombreCompleto = new NombreCompleto();
        nombreCompleto.setNombre(nombre);
        nombreCompleto.setApellido(apellido);
        estudianteDAO.eliminarEstudiantePorNombre(nombreCompleto);
        System.out.println(String.format("Estudiante %s %s eliminado satisfactoriamente", nombre, apellido));
    }

    public void cargarDTOEstudiantesLibros() {
        List<EstudianteLibroDTO> estudiantesLibros = estudianteDAO.cargarDTOEstudiantesLibros();
        estudiantesLibros.forEach(System.out::println);
    }

    public static void main(String[] args) {
        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        PaisDAO paisDAO = new PaisDAOImpl();
        Lesson16Main main = new Lesson16Main(estudianteDAO, paisDAO);
        String separador = "-";
        int repeticiones = 160;
        main.mostrarTodosLosEstudiantes();
        System.out.println(separador.repeat(repeticiones));
        main.mostrarEstudiantePorId(8);
        System.out.println(separador.repeat(repeticiones));
        main.mostrarEstudiantesConNivelAcademico(NivelAcademico.DOCTORADO);
        System.out.println(separador.repeat(repeticiones));
        main.mostrarEstudiantesConFechaNacimientoAnteriorA(LocalDate.of(2006, 1, 1));
        System.out.println(separador.repeat(repeticiones));
        main.mostrarEstudiantesConNombreYNivelAcademico("gene", NivelAcademico.TECNICO);
        System.out.println(separador.repeat(repeticiones));
        main.contarEstudiantesDePais("Mexico");
        System.out.println(separador.repeat(repeticiones));
        main.desactivarEstudiantesConListaDeIds(List.of(12L, 13L, 14L));
        System.out.println(separador.repeat(repeticiones));
        main.eliminarEstudiantePorNombreCompleto("Nombre-2", "Apellido-2");
        System.out.println(separador.repeat(repeticiones));
        main.cargarDTOEstudiantesLibros();
    }
}
