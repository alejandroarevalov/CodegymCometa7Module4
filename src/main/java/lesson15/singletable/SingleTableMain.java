package lesson15.singletable;

import entities.singletable.STAutomovil;
import entities.singletable.STAvion;
import entities.singletable.STMotocicleta;
import entities.singletable.STVehiculo;
import lesson15.singletable.dao.STAutomovilDAO;
import lesson15.singletable.dao.STAutomovilDAOImpl;
import lesson15.singletable.dao.STAvionDAO;
import lesson15.singletable.dao.STAvionDAOImpl;
import lesson15.singletable.dao.STMotocicletaDAO;
import lesson15.singletable.dao.STMotocicletaDAOImpl;
import lesson15.singletable.dao.STVehiculoDAO;
import lesson15.singletable.dao.STVehiculoDAOImpl;

import java.util.List;

public class SingleTableMain {

    private final STAutomovilDAO automovilDAO;
    private final STMotocicletaDAO motocicletaDAO;
    private final STAvionDAO avionDAO;
    private final STVehiculoDAO vehiculoDAO;

    public SingleTableMain(STAutomovilDAO automovilDAO, STMotocicletaDAO motocicletaDAO,
                           STAvionDAO avionDAO, STVehiculoDAO vehiculoDAO) {
        this.automovilDAO = automovilDAO;
        this.motocicletaDAO = motocicletaDAO;
        this.avionDAO = avionDAO;
        this.vehiculoDAO = vehiculoDAO;
    }

    public void insertarSTAutomovil() {
        STAutomovil auto = new STAutomovil();
        auto.setMarca("Kia");
        auto.setModelo("Rio");
        auto.setAnio(2023);
        auto.setPuertas(4);
        auto.setTieneAirbag(true);
        auto.setTipoCombustible("Gasolina");
        auto = automovilDAO.guardar(auto);
        System.out.println("Automóvil guardado con éxito: " + auto);
    }

    public void actualizarSTAvion(int id) {
        STAvion avion = avionDAO.buscarPorId(id);
        avion.setAlcanceKilometros(avion.getAlcanceKilometros() + 500);
        avion = avionDAO.actualizar(avion);
        System.out.println("Avión actualizado: " + avion);
    }

    public void mostrarTodasLasMotocicletas() {
        List<STMotocicleta> motocicletas;
        motocicletas = motocicletaDAO.buscarTodos(0, 100);
        motocicletas.forEach(System.out::println);
    }

    public void mostrarTodosLosAviones() {
        List<STAvion> aviones = avionDAO.buscarTodos(0, 100);
        aviones.forEach(System.out::println);
    }

    public void insertarSTAvionPolimorfico() {
        STAvion nuevoAvion = new STAvion();

        nuevoAvion.setMarca("Embraer");
        nuevoAvion.setModelo("E190");
        nuevoAvion.setAnio(2018);
        nuevoAvion.setTipoMotor("Turbofan");
        nuevoAvion.setCapacidadPasajeros(98);
        nuevoAvion.setAlcanceKilometros(4400.66);
        nuevoAvion = (STAvion) vehiculoDAO.guardar(nuevoAvion);
        System.out.println("Avión guardado de forma polimórfica: " + nuevoAvion);
    }

    public void mostrarTodosLosVehiculos() {
        List<STVehiculo> vehiculos = vehiculoDAO.buscarTodos(0, 20);
        vehiculos.forEach(v ->
            System.out.println(v.getClass().getSimpleName() + ": " + v));
    }

    public static void main(String[] args) {
        STAutomovilDAO automovovilDAO = new STAutomovilDAOImpl();
        STMotocicletaDAO motocicletaDAO = new STMotocicletaDAOImpl();
        STAvionDAO avionDAO = new STAvionDAOImpl();
        STVehiculoDAO vehiculoDAO = new STVehiculoDAOImpl();
        SingleTableMain main = new SingleTableMain(automovovilDAO, motocicletaDAO, avionDAO, vehiculoDAO);
        String separador = "-";
        int repeticiones = 120;
        main.insertarSTAutomovil();
        System.out.println(separador.repeat(repeticiones));
        main.actualizarSTAvion(7);
        System.out.println(separador.repeat(repeticiones));
        main.mostrarTodasLasMotocicletas();
        System.out.println(separador.repeat(repeticiones));
        main.mostrarTodosLosVehiculos();
        System.out.println(separador.repeat(repeticiones));
        main.insertarSTAvionPolimorfico();
        main.mostrarTodosLosAviones();



    }
}
