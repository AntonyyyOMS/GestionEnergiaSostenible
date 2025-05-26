package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controlador.ProyectoEnergiaControlador;
import modelo.ProyectoEnergia; // Sigue siendo necesario para el modelo base

public class ProyectoEnergiaCardGUI extends JFrame implements ActionListener {

    // Componentes para Crear Proyecto
    private JTextField crearNombreTextField, crearCapacidadTextField;
    private JComboBox<String> crearTipoFuenteComboBox;
    private JButton crearButton;

    private JTextArea detallesTextArea; // Área para mostrar mensajes y resultados

    // Instancia del controlador
    private ProyectoEnergiaControlador controlador;

    public ProyectoEnergiaCardGUI() {
        setTitle("Gestión de Proyectos de Energía Sostenible - Crear Proyecto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 400); // Tamaño ajustado para solo el módulo de crear y el checkbox
        setLayout(new BorderLayout());
        // Usar color de la paleta para el fondo principal
        getContentPane().setBackground(PaletaUI.FONDO_PRINCIPAL_BLANCO_CALIDO);

        // Inicializar el controlador, pasándole el JTextArea para mensajes
        detallesTextArea = new JTextArea();
        detallesTextArea.setEditable(false);
        detallesTextArea.setLineWrap(true);
        detallesTextArea.setWrapStyleWord(true);
        detallesTextArea.setBackground(PaletaUI.FONDO_SECCIONES_AZUL_CLARO); // Color de fondo para el área de texto
        detallesTextArea.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO); // Color de texto principal
        JScrollPane scrollPane = new JScrollPane(detallesTextArea);
        scrollPane.setPreferredSize(new Dimension(480, 150)); // Ajusta el tamaño

        this.controlador = new ProyectoEnergiaControlador(detallesTextArea);

        // Panel Crear
        JPanel crearPanel = new JPanel(new GridBagLayout());
        crearPanel.setBackground(PaletaUI.FONDO_PRINCIPAL_BLANCO_CALIDO); // Usar color de la paleta
        crearPanel.setBorder(BorderFactory.createLineBorder(PaletaUI.BORDES_SOMBRAS_GRIS_CLARO, 2)); // Borde suave
        addCrearComponents(crearPanel);

        add(crearPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH); // Añade el área de texto en la parte inferior

        // Muestra un mensaje inicial
        detallesTextArea.append("Aplicación de Gestión de Proyectos de Energía Iniciada.\n");
        detallesTextArea.append("Intentando conectar a la base de datos a través del Modelo...\n");

        setVisible(true);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
    }

    private void addCrearComponents(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Margen entre componentes ajustado
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nombreLabel = new JLabel("Nombre del Proyecto:");
        nombreLabel.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        crearNombreTextField = new JTextField(25);
        crearNombreTextField.setBackground(PaletaUI.FONDO_SECCIONES_AZUL_CLARO);
        crearNombreTextField.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        crearNombreTextField.setBorder(BorderFactory.createLineBorder(PaletaUI.BORDES_SOMBRAS_GRIS_CLARO, 1));
        panel.add(crearNombreTextField, gbc);

        // Tipo de Fuente
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel tipoFuenteLabel = new JLabel("Tipo de Fuente:");
        tipoFuenteLabel.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        panel.add(tipoFuenteLabel, gbc);
        gbc.gridx = 1;
        String[] tiposFuente = {"Solar", "Eólica", "Hidroeléctrica", "Geotérmica", "Biomasa"};
        crearTipoFuenteComboBox = new JComboBox<>(tiposFuente);
        crearTipoFuenteComboBox.setBackground(PaletaUI.FONDO_SECCIONES_AZUL_CLARO);
        crearTipoFuenteComboBox.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        panel.add(crearTipoFuenteComboBox, gbc);

        // Capacidad
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel capacidadLabel = new JLabel("Capacidad (MW):");
        capacidadLabel.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        panel.add(capacidadLabel, gbc);
        gbc.gridx = 1;
        crearCapacidadTextField = new JTextField(25);
        crearCapacidadTextField.setBackground(PaletaUI.FONDO_SECCIONES_AZUL_CLARO);
        crearCapacidadTextField.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO);
        crearCapacidadTextField.setBorder(BorderFactory.createLineBorder(PaletaUI.BORDES_SOMBRAS_GRIS_CLARO, 1));
        panel.add(crearCapacidadTextField, gbc);

        // Botón Crear
        gbc.gridx = 0;
        gbc.gridy = 3; // Ajusta la fila ya que no hay checkbox
        gbc.gridwidth = 2; // Ocupa dos columnas
        gbc.anchor = GridBagConstraints.CENTER;
        crearButton = new JButton("Crear Proyecto");
        crearButton.setBackground(PaletaUI.PRIMARIO_AMARILLO_ENERGIA); // Color de botón primario
        crearButton.setForeground(PaletaUI.TEXTO_PRINCIPAL_GRIS_OSCURO); // Texto oscuro para contraste
        crearButton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente negrita
        crearButton.setBorder(BorderFactory.createLineBorder(PaletaUI.ENFASIS_ECOLOGICO_VERDE_OSCURO, 2)); // Borde ecológico
        crearButton.addActionListener(this);
        panel.add(crearButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == crearButton) {
            confirmarYCrearProyecto(); // Llama al método que incluye la confirmación
        }
    }

    /**
     * Muestra una ventana de confirmación antes de proceder con la creación del proyecto.
     * Esta es la aplicación del "Decorator" a la acción de creación.
     */
    private void confirmarYCrearProyecto() {
        String nombre = crearNombreTextField.getText();
        String tipoFuente = (String) crearTipoFuenteComboBox.getSelectedItem();
        String capacidadStr = crearCapacidadTextField.getText();

        if (nombre.isEmpty() || capacidadStr.isEmpty()) {
            detallesTextArea.append("Error: Nombre y Capacidad son campos obligatorios para crear.\n");
            return;
        }

        try {
            double capacidad = Double.parseDouble(capacidadStr);

            // Mensaje de confirmación que actúa como nuestro "Decorator de Acción"
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de crear el proyecto:\n" +
                "Nombre: " + nombre + "\n" +
                "Fuente: " + tipoFuente + "\n" +
                "Capacidad: " + capacidad + " MW?",
                "Confirmar Creación de Proyecto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                // Si el usuario confirma, entonces procedemos con la creación
                int idCreado = controlador.crearProyectoEnergia(nombre, tipoFuente, capacidad);

                if (idCreado > 0) {
                    detallesTextArea.append("Proyecto creado con ID: " + idCreado + "\n");
                    detallesTextArea.append("Detalles del proyecto: " + nombre + ", " + tipoFuente + ", " + capacidad + " MW\n");
                } else {
                    detallesTextArea.append("Error al crear el proyecto en la base de datos.\n");
                }
                limpiarCamposCrear();
            } else {
                detallesTextArea.append("Creación de proyecto cancelada por el usuario.\n");
            }

        } catch (NumberFormatException ex) {
            detallesTextArea.append("Error: La capacidad debe ser un número válido.\n");
        } catch (Exception ex) {
            detallesTextArea.append("Error al crear proyecto: " + ex.getMessage() + "\n");
            ex.printStackTrace();
        }
    }

    private void limpiarCamposCrear() {
        crearNombreTextField.setText("");
        crearCapacidadTextField.setText("");
        crearTipoFuenteComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProyectoEnergiaCardGUI::new);
    }
}