package finalproyect;

import entities.Estudiante;
import entities.NombreCompleto;
import finalproyect.dao.MySQLDAO;
import finalproyect.dao.RedisDAO;
import finalproyect.repository.EstudianteRepository;

import java.util.List;

public class Lesson19Main {

    private final EstudianteRepository estudianteRepository;

    public Lesson19Main(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public void buscarTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteRepository.buscarTodos();
        estudiantes.forEach(System.out::println);
    }

    public void buscarEstudiantePorId(Integer id) {
        Estudiante estudiante = estudianteRepository.buscarPorId(id);
        System.out.println(estudiante);
    }

    public void guardarEstudiante(Estudiante estudiante) {
        Estudiante nuevo = new Estudiante();
        nuevo.setNombreCompleto(new NombreCompleto("Celia", "Cruz"));
    }

    public void actualizarEstudiante(Estudiante estudiante) {

    }

    public void eliminarEstudiante(Estudiante estudiante) {

    }

    public static void main(String[] args) {
        MySQLDAO mySQLDAO = new MySQLDAO();
        RedisDAO redisDAO = new RedisDAO();
        EstudianteRepository estudianteRepository = new EstudianteRepository(mySQLDAO, redisDAO);
        Lesson19Main lesson19Main = new Lesson19Main(estudianteRepository);
        lesson19Main.buscarTodosLosEstudiantes();
        // lesson19Main.buscarEstudiantePorId(8);
    }
}
