package lesson13.dao;

import entities.Pais;

import java.util.List;

public interface PaisDAO extends GenericDAO<Pais> {

    List<Pais> buscarPaisesConEstudiantes();
}
