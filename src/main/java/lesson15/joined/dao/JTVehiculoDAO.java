package lesson15.joined.dao;

import java.util.List;

public interface JTVehiculoDAO {

    <T> List<T> buscarTodosConTipo(Class<T> subTipo, int offset, int limit);

    <T> T buscarPorIdConTipo(Class<T> clazz, int id);

    <T> T guardar(T entidad);

    <T> T actualizar(T entidad);

    <T> void eliminar(T entidad);
}
