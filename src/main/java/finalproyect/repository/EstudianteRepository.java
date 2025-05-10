package finalproyect.repository;

import entities.Estudiante;
import finalproyect.dao.MySQLDAO;
import finalproyect.dao.RedisDAO;

import java.util.List;

public class EstudianteRepository {

    private final MySQLDAO mySQLDAO;
    private final RedisDAO redisDAO;

    public EstudianteRepository(MySQLDAO mySQLDAO, RedisDAO redisDAO) {
        this.mySQLDAO = mySQLDAO;
        this.redisDAO = redisDAO;
    }

    public Estudiante buscarPorId(Integer id) {
        Estudiante estudiante = null;
        estudiante = redisDAO.buscarPorId(Estudiante.class, id);
        if (estudiante == null) {
            estudiante = mySQLDAO.buscarPorId(Estudiante.class, id);
            redisDAO.guardar(estudiante);
        }
        return estudiante;
    }

    public List<Estudiante> buscarTodos() {
        List<Estudiante> resultados = redisDAO.buscarTodos(Estudiante.class);
        if (resultados == null || resultados.isEmpty()) {
            resultados = mySQLDAO.buscarTodos(Estudiante.class);
            for (Estudiante estudiante : resultados) {
                redisDAO.guardar(estudiante);
            }
        }
        return resultados;
    }

    public Estudiante guardar(Estudiante estudiante) {
        estudiante = mySQLDAO.guardar(estudiante);
        redisDAO.guardar(estudiante);
        return estudiante;
    }

    public Estudiante actualizar(Estudiante estudiante) {
        estudiante = mySQLDAO.actualizar(estudiante);
        redisDAO.actualizar(estudiante);
        return estudiante;
    }

    public void eliminar(Estudiante estudiante) {
        mySQLDAO.eliminar(estudiante);
        redisDAO.eliminar(estudiante);
    }
}
