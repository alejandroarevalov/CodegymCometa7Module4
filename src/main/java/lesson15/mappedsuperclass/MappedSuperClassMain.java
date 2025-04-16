package lesson15.mappedsuperclass;

import entities.mappedsuperclass.MSAutomovil;
import entities.mappedsuperclass.MSAvion;
import entities.mappedsuperclass.MSMotocicleta;
import lesson15.mappedsuperclass.dao.MSAutomovilDAO;
import lesson15.mappedsuperclass.dao.MSAutomovilDAOImpl;
import lesson15.mappedsuperclass.dao.MSAvionDAO;
import lesson15.mappedsuperclass.dao.MSAvionDAOImpl;
import lesson15.mappedsuperclass.dao.MSMotocicletaDAO;
import lesson15.mappedsuperclass.dao.MSMotocicletaDAOImpl;

import java.util.List;

public class MappedSuperClassMain {

    private final MSAutomovilDAO automovilDAO;
    private final MSMotocicletaDAO motocicletaDAO;
    private final MSAvionDAO avionDAO;

    public MappedSuperClassMain(MSAutomovilDAO automovilDAO, MSMotocicletaDAO motocicletaDAO, MSAvionDAO avionDAO) {
        this.automovilDAO = automovilDAO;
        this.motocicletaDAO = motocicletaDAO;
        this.avionDAO = avionDAO;
    }

    public void mostrarTodosLosAutomoviles() {
        List<MSAutomovil> automoviles = automovilDAO.buscarTodos(0, 1000);
        automoviles.forEach(System.out::println);
    }

    public void insertarNuevaMotocicleta() {
        MSMotocicleta nuevaMotocicleta = new MSMotocicleta();
        nuevaMotocicleta.setCilindraje(1000);
        nuevaMotocicleta.setMarca("BMW");
        nuevaMotocicleta.setModelo("S 1000 RR");
        nuevaMotocicleta.setAnio(2025);
        nuevaMotocicleta.setTipoSuspension("Basculante doble amortiguador");
        nuevaMotocicleta.setTieneMaletero(false);
        nuevaMotocicleta = motocicletaDAO.guardar(nuevaMotocicleta);
        System.out.println("Nueva motocicleta creada: " + nuevaMotocicleta);
    }

    public void actualizarAvion() {
        MSAvion avion = avionDAO.buscarPorId(1);
        avion.setMarca("Lockheed Martin");
        avion.setModelo("L-1011");
        avion.setAnio(2025);
        avion = avionDAO.actualizar(avion);
        System.out.println("Avion actualizada: " + avion);
    }

    public static void main(String[] args) {
        MSAutomovilDAO automovilDAO = new MSAutomovilDAOImpl();
        MSMotocicletaDAO motocicletaDAO = new MSMotocicletaDAOImpl();
        MSAvionDAO avionDAO = new MSAvionDAOImpl();
        MappedSuperClassMain main = new MappedSuperClassMain(automovilDAO, motocicletaDAO, avionDAO);
        String separador = "-";
        int repeticiones = 120;

        main.mostrarTodosLosAutomoviles();
        System.out.println(separador.repeat(repeticiones));
        main.insertarNuevaMotocicleta();
        System.out.println(separador.repeat(repeticiones));
        main.actualizarAvion();
    }
}
