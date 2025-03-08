package lesson7;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        EjemplosJDBC ejemplosJDBC = new EjemplosJDBC();
        ejemplosJDBC.ejecutarConsulta();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.obtenerDatosDelResultSet();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.obtenerDatosFilaActual();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.obtenerMetadatosDelResultSet();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.manejoDeNulls();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.manejoDeFechas();
        System.out.println("----------------------------------------------------------------------------------");
        ejemplosJDBC.usarMetodoExecuteDeResultSet();

        ejemplosJDBC.cerrarConexion();
    }

}
