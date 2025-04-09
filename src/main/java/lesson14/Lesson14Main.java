package lesson14;

import entities.Estudiante;
import lesson14.dao.EstudianteDAO;
import lesson14.dao.EstudianteDAOImpl;

public class Lesson14Main {

    private final EstudianteDAO estudianteDAO;

    public Lesson14Main(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public void buscarEstudiantePorId(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorId(id);
        //TODO: Usar depurador en el ejemplo
        System.out.println(estudiante);
    }

    public void buscarEstudiantePorIdUsandoJoinFetch(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorIdUsandoFetchJoin(id);
        //TODO: Usar depurador en el ejemplo
        System.out.println(estudiante);
    }

    public void buscarEstudiantePorIdUsandoCacheDePrimerNivel(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorIdMostrandoCacheDePrimerNivel(id);
        System.out.println(estudiante);
    }

    public void buscarEstudiantePorIdUsandoCacheDeSegundoNivel(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorIdUsando2SesionesConCacheDeSegundoNivel(id);
        System.out.println(estudiante);
    }

    public void buscarEstudiantePorIdUsandCacheDeConsultas(int id) {
        Estudiante estudiante = estudianteDAO.buscarPorIdUsando2SesionesConCacheDeConsultas(id);
        System.out.println(estudiante);
    }

    public void borrarEstudianteDeCache(int id) {
        estudianteDAO.borrarEstudianteDeCache(3);
    }

    public static void main(String[] args) {
        EstudianteDAO estudianteDAO = new EstudianteDAOImpl();
        Lesson14Main main = new Lesson14Main(estudianteDAO);
        String dash = "-";
        int repetitions = 200;
        /*
        main.buscarEstudiantePorId(1);
        System.out.println(dash.repeat(repetitions));
        main.buscarEstudiantePorIdUsandoJoinFetch(1);
        System.out.println(dash.repeat(repetitions));
        main.buscarEstudiantePorIdUsandoCacheDePrimerNivel(3);
        System.out.println(dash.repeat(repetitions));
        main.buscarEstudiantePorIdUsandoCacheDeSegundoNivel(4);
        System.out.println(dash.repeat(repetitions));
        main.buscarEstudiantePorIdUsandCacheDeConsultas(6);
        System.out.println(dash.repeat(repetitions));
        */
        main.borrarEstudianteDeCache(5);

    }
}
