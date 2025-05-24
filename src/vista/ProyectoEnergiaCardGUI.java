package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProyectoEnergiaCardGUI extends JFrame implements ActionListener {

    private JTextField crearNombreTextField;
    private JComboBox<String> crearTipoFuenteComboBox;
    private JTextField crearCapacidadTextField;
    private JButton crearButton;

    private JTextField buscarIdTextField;
    private JButton buscarButton;

    private JTextField actualizarIdTextField;
    private JTextField actualizarNombreTextField;
    private JComboBox<String> actualizarTipoFuenteComboBox;
    private JTextField actualizarCapacidadTextField;
    private JButton actualizarButton;

    private JTextField eliminarIdTextField;
    private JButton eliminarButton;

    private JTextArea detallesTextArea;

    // --- Conexión a la Base de Datos ---
    private Connection conexion;
    private String url = "jdbc:sqlserver://your_server_name;databaseName=your_database_name;integratedSecurity=true;"; // Cambiar
    // private String url = "jdbc:mysql://localhost:3306/your_database_name"; // Ejemplo para MySQL
    private String usuario = "your_username"; // Cambiar
    private String contraseña = "your_password"; // Cambiar
    private boolean conexionEstablecida = false; // Track connection status

    public ProyectoEnergiaCardGUI() {
        setTitle("Gestión de Proyectos de Energía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(238, 238, 238));

        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));

        // --- Panel de Creación (Card) ---
        JPanel crearPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        crearPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Crear Proyecto"));
        crearPanel.setBackground(new Color(245, 245, 245));

        JLabel crearNombreLabel = new JLabel("Nombre:");
        crearNombreTextField = new JTextField(20);
        JLabel crearTipoFuenteLabel = new JLabel("Tipo de Fuente:");
        crearTipoFuenteComboBox = new JComboBox<>(new String[]{"Solar", "Eólico", "Hidroeléctrico", "Biomasa"});
        JLabel crearCapacidadLabel = new JLabel("Capacidad (MW):");
        crearCapacidadTextField = new JTextField(10);
        crearButton = new JButton("Crear");
        crearButton.addActionListener(this);
        crearButton.setBackground(new Color(0, 123, 255));
        crearButton.setForeground(Color.WHITE);

        crearPanel.add(crearNombreLabel);
        crearPanel.add(crearNombreTextField);
        crearPanel.add(crearTipoFuenteLabel);
        crearPanel.add(crearTipoFuenteComboBox);
        crearPanel.add(crearCapacidadLabel);
        crearPanel.add(crearCapacidadTextField);
        crearPanel.add(new JLabel());
        crearPanel.add(crearButton);

        mainPanel.add(crearPanel);

        // --- Panel de Búsqueda (Card) ---
        JPanel buscarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buscarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Buscar Proyecto"));
        buscarPanel.setBackground(new Color(245, 245, 245));

        JLabel buscarIdLabel = new JLabel("ID del Proyecto:");
        buscarIdTextField = new JTextField(10);
        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this);
        buscarButton.setBackground(new Color(255, 193, 7));
        buscarButton.setForeground(Color.BLACK);

        buscarPanel.add(buscarIdLabel);
        buscarPanel.add(buscarIdTextField);
        buscarPanel.add(buscarButton);

        mainPanel.add(buscarPanel);

        // --- Panel de Actualización (Card) ---
        JPanel actualizarPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        actualizarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Actualizar Proyecto"));
        actualizarPanel.setBackground(new Color(245, 245, 245));

        JLabel actualizarIdLabel = new JLabel("ID a Actualizar:");
        actualizarIdTextField = new JTextField(10);
        JLabel actualizarNombreLabel = new JLabel("Nuevo Nombre:");
        actualizarNombreTextField = new JTextField(20);
        JLabel actualizarTipoFuenteLabel = new JLabel("Nuevo Tipo de Fuente:");
        actualizarTipoFuenteComboBox = new JComboBox<>(new String[]{"Solar", "Eólico", "Hidroeléctrico", "Biomasa"});
        JLabel actualizarCapacidadLabel = new JLabel("Nueva Capacidad (MW):");
        actualizarCapacidadTextField = new JTextField(10);
        actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener(this);
        actualizarButton.setBackground(new Color(76, 175, 80));
        actualizarButton.setForeground(Color.WHITE);

        actualizarPanel.add(actualizarIdLabel);
        actualizarPanel.add(actualizarIdTextField);
        actualizarPanel.add(actualizarNombreLabel);
        actualizarPanel.add(actualizarNombreTextField);
        actualizarPanel.add(actualizarTipoFuenteLabel);
        actualizarPanel.add(actualizarTipoFuenteComboBox);
        actualizarPanel.add(actualizarCapacidadLabel);
        actualizarPanel.add(actualizarCapacidadTextField);
        actualizarPanel.add(new JLabel());
        actualizarPanel.add(actualizarButton);

        mainPanel.add(actualizarPanel);

        // --- Panel de Eliminación (Card) ---
        JPanel eliminarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        eliminarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Eliminar Proyecto"));
        eliminarPanel.setBackground(new Color(245, 245, 245));

        JLabel eliminarIdLabel = new JLabel("ID a Eliminar:");
        eliminarIdTextField = new JTextField(10);
        eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(this);
        eliminarButton.setBackground(new Color(220, 53, 69));
        eliminarButton.setForeground(Color.WHITE);

        eliminarPanel.add(eliminarIdLabel);
        eliminarPanel.add(eliminarIdTextField);
        eliminarPanel.add(eliminarButton);

        mainPanel.add(eliminarPanel);

        add(mainPanel, BorderLayout.CENTER);

        // --- Área de Detalles ---
        JPanel detallesPanel = new JPanel(new BorderLayout());
        detallesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Detalles / Mensajes"));
        detallesPanel.setBackground(new Color(245, 245, 245));
        detallesTextArea = new JTextArea(5, 40);
        detallesTextArea.setEditable(false);
        JScrollPane detallesScrollPane = new JScrollPane(detallesTextArea);
        detallesPanel.add(detallesScrollPane, BorderLayout.CENTER);

        add(detallesPanel, BorderLayout.SOUTH);

        // --- Inicializar Conexión a la Base de Datos ---
        try {
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); // Para SQL Server
            // Class.forName("com.mysql.jdbc.Driver"); // Para MySql
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            if (conexion != null) {
                conexionEstablecida = true;
                detallesTextArea.append("Conexión a la base de datos establecida.\n");
            } else {
                detallesTextArea.append("Error: No se pudo establecer la conexión a la base de datos.\n");
            }
        } catch (SQLException e) {
            detallesTextArea.append("Error al conectar a la base de datos: " + e.getMessage() + "\n");
            conexion = null;
            conexionEstablecida = false;
            // Puedes optar por salir de la aplicación aquí si la conexión es esencial.
            // System.exit(1);
        }
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == crearButton) {
            crearProyecto();
        } else if (e.getSource() == buscarButton) {
            buscarProyecto();
        } else if (e.getSource() == actualizarButton) {
            actualizarProyecto();
        } else if (e.getSource() == eliminarButton) {
            eliminarProyecto();
        }
    }

    private void crearProyecto() {
        String nombre = crearNombreTextField.getText();
        String tipoFuente = (String) crearTipoFuenteComboBox.getSelectedItem();
        String capacidadStr = crearCapacidadTextField.getText();

        if (nombre.isEmpty() || capacidadStr.isEmpty()) {
            detallesTextArea.append("Error: Nombre y Capacidad son campos obligatorios.\n");
            return;
        }

        try {
            double capacidad = Double.parseDouble(capacidadStr);
            String sql = "INSERT INTO tlistagestionenergia08 (nombreProyec, tipoFuente, capacidadMW) VALUES (?, ?, ?)";
            if (conexionEstablecida) { // Check if connection is established
                PreparedStatement pstmt = conexion.prepareStatement(sql);
                pstmt.setString(1, nombre);
                pstmt.setString(2, tipoFuente);
                pstmt.setDouble(3, capacidad);
                int filasInsertadas = pstmt.executeUpdate();

                if (filasInsertadas > 0) {
                    detallesTextArea.append(String.format("Proyecto '%s' creado exitosamente.\n", nombre));
                    limpiarCamposCrear();
                } else {
                    detallesTextArea.append("Error al crear el proyecto.\n");
                }
                pstmt.close();
            } else {
                detallesTextArea.append("Error: No hay conexión a la base de datos.\n");
            }
        } catch (NumberFormatException ex) {
            detallesTextArea.append("Error: La capacidad debe ser un número válido.\n");
        } catch (SQLException ex) {
            detallesTextArea.append("Error de base de datos al crear proyecto: " + ex.getMessage() + "\n");
        }
    }

    private void buscarProyecto() {
        String idStr = buscarIdTextField.getText();
        if (idStr.isEmpty()) {
            detallesTextArea.append("Error: Debe ingresar un ID para buscar.\n");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            String sql = "SELECT nombreProyec, tipoFuente, capacidadMW FROM tlistagestionenergia08 WHERE id = ?";
            if (conexionEstablecida) {
                PreparedStatement pstmt = conexion.prepareStatement(sql);
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String nombre = rs.getString("nombreProyec");
                    String tipoFuente = rs.getString("tipoFuente");
                    double capacidad = rs.getDouble("capacidadMW");
                    detallesTextArea.append(String.format("Proyecto encontrado: ID=%d, Nombre=%s, Tipo=%s, Capacidad=%.2f MW\n", id, nombre, tipoFuente, capacidad));
                } else {
                    detallesTextArea.append(String.format("No se encontró ningún proyecto con ID: %d\n", id));
                }
                rs.close();
                pstmt.close();
                limpiarCamposBuscar();
            } else {
                detallesTextArea.append("Error: No hay conexión a la base de datos.\n");
            }
        } catch (NumberFormatException ex) {
            detallesTextArea.append("Error: El ID debe ser un número entero.\n");
        } catch (SQLException ex) {
            detallesTextArea.append("Error de base de datos al buscar proyecto: " + ex.getMessage() + "\n");
        }
    }

    private void actualizarProyecto() {
        String idStr = actualizarIdTextField.getText();
        String nombre = actualizarNombreTextField.getText();
        String tipoFuente = (String) actualizarTipoFuenteComboBox.getSelectedItem();
        String capacidadStr = actualizarCapacidadTextField.getText();

        if (idStr.isEmpty()) {
            detallesTextArea.append("Error: Debe ingresar el ID del proyecto a actualizar.\n");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double capacidad = 0;
            if (!capacidadStr.isEmpty()) {
                capacidad = Double.parseDouble(capacidadStr);
            }

            // Construir la parte SET de la consulta dinámicamente
            StringBuilder sqlBuilder = new StringBuilder("UPDATE tlistagestionenergia08 SET ");
            if (!nombre.isEmpty()) {
                sqlBuilder.append("nombreProyec = ?, ");
            }
            sqlBuilder.append("tipoFuente = ?, "); // TipoFuente siempre se actualiza
            if (!capacidadStr.isEmpty()) {
                sqlBuilder.append("capacidadMW = ?, ");
            }
            // Eliminar la coma extra al final
            sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
            sqlBuilder.append(" WHERE id = ?");

            if (conexionEstablecida) {
                PreparedStatement pstmt = conexion.prepareStatement(sqlBuilder.toString());

                int paramIndex = 1;
                if (!nombre.isEmpty()) {
                    pstmt.setString(paramIndex++, nombre);
                }
                pstmt.setString(paramIndex++, tipoFuente);
                if (!capacidadStr.isEmpty()) {
                    pstmt.setDouble(paramIndex++, capacidad);
                }
                pstmt.setInt(paramIndex, id);

                int filasActualizadas = pstmt.executeUpdate();

                if (filasActualizadas > 0) {
                    detallesTextArea.append(String.format("Proyecto con ID %d actualizado exitosamente.\n", id));
                    limpiarCamposActualizar();
                } else {
                    detallesTextArea.append(String.format("No se pudo actualizar el proyecto con ID: %d (Puede que no exista).\n", id));
                }
                pstmt.close();
            } else {
                detallesTextArea.append("Error: No hay conexión a la base de datos.\n");
            }
        } catch (NumberFormatException ex) {
            detallesTextArea.append("Error: El ID y la Capacidad deben ser números válidos.\n");
        } catch (SQLException ex) {
            detallesTextArea.append("Error de base de datos al actualizar proyecto: " + ex.getMessage() + "\n");
        }
    }

    private void eliminarProyecto() {
        String idStr = eliminarIdTextField.getText();
        if (idStr.isEmpty()) {
            detallesTextArea.append("Error: Debe ingresar el ID del proyecto a eliminar.\n");
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            String sql = "DELETE FROM tlistagestionenergia08 WHERE id = ?";
            if (conexionEstablecida) {
                PreparedStatement pstmt = conexion.prepareStatement(sql);
                pstmt.setInt(1, id);
                int filasEliminadas = pstmt.executeUpdate();
                if (filasEliminadas > 0) {
                    detallesTextArea.append(String.format("Proyecto con ID %d eliminado exitosamente.\n", id));
                    limpiarCampoEliminar();
                } else {
                    detallesTextArea.append(String.format("No se pudo eliminar el proyecto con ID: %d (Puede que no exista).\n", id));
                }
                pstmt.close();
            } else {
                detallesTextArea.append("Error: No hay conexión a la base de datos.\n");
            }
        } catch (NumberFormatException ex) {
            detallesTextArea.append("Error: El ID debe ser un número entero.\n");
        } catch (SQLException ex) {
            detallesTextArea.append("Error de base de datos al eliminar proyecto: " + ex.getMessage() + "\n");
        }
    }

    private void limpiarCamposCrear() {
        crearNombreTextField.setText("");
        crearCapacidadTextField.setText("");
    }

    private void limpiarCamposBuscar() {
        buscarIdTextField.setText("");
    }

    private void limpiarCamposActualizar() {
        actualizarIdTextField.setText("");
        actualizarNombreTextField.setText("");
        actualizarCapacidadTextField.setText("");
    }

    private void limpiarCampoEliminar() {
        eliminarIdTextField.setText("");
    }

    // Método para cerrar la conexión a la base de datos cuando se cierra la aplicación
    @Override
    protected void finalize() throws Throwable {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            detallesTextArea.append("Conexión a la base de datos cerrada.\n");
        }
        super.finalize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProyectoEnergiaCardGUI::new);
    }
}

