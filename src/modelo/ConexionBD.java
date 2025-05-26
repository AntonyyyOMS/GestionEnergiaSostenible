package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaGestionEnergiaDB;encrypt=false;trustServerCertificate=true;";
    private final String usuario = "sa"; // Tu usuario de SQL Server
    private final String clave = "axia2005"; // Tu contraseña de SQL Server

    private ConexionBD() {
        try {
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Ya no es estrictamente necesario
            conexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public static void cerrarConexion() {
        if (instancia != null && instancia.conexion != null) {
            try {
                instancia.conexion.close();
                System.out.println("Conexión a la base de datos cerrada.");
                instancia = null;
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}