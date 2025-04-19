package lesson16.dao;

import entities.Pais;
import lesson16.generic.GenericDAO;

public interface PaisDAO extends GenericDAO {

    Pais buscarPorNombre(String nombre);
}
