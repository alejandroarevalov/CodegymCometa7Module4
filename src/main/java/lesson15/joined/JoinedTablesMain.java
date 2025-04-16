package lesson15.joined;

import entities.joined.JTAutomovil;
import entities.joined.JTAvion;
import entities.joined.JTMotocicleta;
import entities.joined.JTVehiculo;
import lesson15.joined.dao.JTVehiculoDAO;
import lesson15.joined.dao.JTVehiculoDAOImpl;

import java.util.List;

public class JoinedTablesMain {

    private final JTVehiculoDAO vehiculoDAO;

    public JoinedTablesMain(JTVehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    public void buscarTodosLosVehiculos() {
        List<JTVehiculo> listaVehiculos = vehiculoDAO.buscarTodosConTipo(JTVehiculo.class, 0, 100);
        listaVehiculos.forEach(System.out::println);
    }

    public void buscarTodasLasMotocicletas() {
        List<JTMotocicleta> listaMotocicletas = vehiculoDAO.buscarTodosConTipo(JTMotocicleta.class, 0, 100);
        listaMotocicletas.forEach(System.out::println);
    }

    public void guardarNuevoAvion() {
        JTAvion avion = new JTAvion();
        avion.setMarca("Lockheed Martin");
        avion.setModelo("F-22 Raptor");
        avion.setAnio(2020);
        avion.setCapacidadPasajeros(1);
        avion.setAlcanceKilometros(2960.0);
        avion.setTipoMotor("Turbofan");
        avion = vehiculoDAO.guardar(avion);
        System.out.println("Avión guardado exitosamente: " + avion);
    }

    public void actualizarAutomovil() {
        JTAutomovil auto = vehiculoDAO.buscarPorIdConTipo(JTAutomovil.class, 1);
        if (auto != null) {
            auto.setTipoCombustible("Eléctrico");
            auto.setTieneAirbag(true);
            vehiculoDAO.actualizar(auto);
            System.out.println("Automóvil actualizado: " + auto);
        } else {
            System.out.println("Automóvil con ID 1 no encontrado.");
        }
    }

    public static void main(String[] args) {
        JTVehiculoDAO vehiculoDAO = new JTVehiculoDAOImpl();
        JoinedTablesMain main = new JoinedTablesMain(vehiculoDAO);
        String separador = "-";
        int repeticiones = 120;
        main.buscarTodosLosVehiculos();
        System.out.println(separador.repeat(repeticiones));
        main.buscarTodasLasMotocicletas();
        System.out.println(separador.repeat(repeticiones));
        main.guardarNuevoAvion();
        System.out.println(separador.repeat(repeticiones));
        main.actualizarAutomovil();
    }
}
