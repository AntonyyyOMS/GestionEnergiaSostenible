package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static ConexionBD instancia;
    private Connection conexion;

    // --- CONFIGURA ESTOS VALORES PARA TU SQL SERVER ---
    // Asegúrate de que estos valores sean correctos para tu instalación de SQL Server.
    // Confirma el nombre de tu servidor (localhost, IP, o nombre de host),
    // el puerto (1433 es el predeterminado), el nombre de tu base de datos,
    // y tus credenciales de usuario/contraseña de SQL Server.

    // Opción 1: Conexión estándar con usuario y contraseña (recomendado para desarrollo local)
    // Usamos encrypt=false y trustServerCertificate=true para evitar problemas de SSL en desarrollo.
    private final String url = "jdbc:sqlserver://localhost:1433;databaseName=SistemaGestionEnergiaDB;encrypt=false;trustServerCertificate=true;";

    // Opción 2: Si tienes una instancia nombrada de SQL Server (ej. SQLEXPRESS)
    // private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=SistemaGestionEnergiaDB;encrypt=false;trustServerCertificate=true;";

    // Opción 3: Si tu SQL Server está en una IP remota
    // private final String url = "jdbc:sqlserver://TU_IP_DEL_SERVIDOR:1433;databaseName=SistemaGestionEnergiaDB;encrypt=false;trustServerCertificate=true;";

    // Tu usuario de SQL Server (ej. 'sa')
    private final String usuario = "sa";
    // Tu contraseña de SQL Server (ej. 'axia2005')
    private final String clave = "axia2005";
    // --- FIN DE CONFIGURACIÓN ---

    // Constructor privado para implementar el patrón Singleton
    private ConexionBD() {
        try {
            // Cargar el driver de SQL Server
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // No es estrictamente necesario desde JDBC 4.0 (Java 6+)
                                                                           // pero no hace daño y asegura la carga si hay problemas.

            // Intentar establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            System.err.println("  URL: " + url);
            System.err.println("  Usuario: " + usuario);
            System.err.println("  Mensaje de error: " + e.getMessage());
            System.err.println("  SQLState: " + e.getSQLState());
            System.err.println("  ErrorCode: " + e.getErrorCode());
            e.printStackTrace(); // Imprime la traza completa de la excepción
            conexion = null; // Asegura que la conexión sea nula si la conexión falla
        } catch (Exception e) { // Capturar cualquier otra excepción (ej. ClassNotFoundException si el driver no se carga)
            System.err.println("Error inesperado al cargar el driver o al conectar: " + e.getMessage());
            e.printStackTrace();
            conexion = null; // Asegura que la conexión sea nula
        }
    }

    // Método estático para obtener la única instancia de ConexionBD (Singleton)
    public static ConexionBD getInstancia() {
        // Doble verificación para Singleton thread-safe (optimización de rendimiento)
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }

    // Método para obtener la conexión activa
    public Connection getConexion() {
        return conexion;
    }

    // Método para cerrar la conexión de la base de datos de forma controlada
    // Es buena práctica cerrar la conexión cuando la aplicación se cierra,
    // pero ten cuidado de no cerrarla prematuramente si otras partes del código la necesitan.
    public static void cerrarConexion() {
        if (instancia != null && instancia.conexion != null) {
            try {
                if (!instancia.conexion.isClosed()) {
                    instancia.conexion.close();
                    System.out.println("Conexión de la base de datos cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión de la base de datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                instancia = null; // Resetea la instancia para permitir una nueva conexión si es necesario
            }
        }
    }
}