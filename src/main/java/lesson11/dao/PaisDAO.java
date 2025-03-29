package lesson11.dao;

import entities.Pais;

public interface PaisDAO {

    Pais buscarPorId(Integer id);

    Pais buscarPorNombre(String nombre);
}
