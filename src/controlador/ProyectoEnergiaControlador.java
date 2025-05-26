package controlador;

import vista.ProyectoEnergiaVista;
import javax.swing.JTextArea;
import modelo.ProyectoEnergia; // Importar ProyectoEnergia si no está ya

public class ProyectoEnergiaControlador {

    private ProyectoEnergiaFacade proyectoEnergiaFacade;
    private ProyectoEnergiaVista proyectoEnergiaVista;

    // Constructor modificado para recibir el JTextArea de la GUI
    public ProyectoEnergiaControlador(JTextArea outputArea) {
        this.proyectoEnergiaFacade = new ProyectoEnergiaFacade();
        this.proyectoEnergiaVista = new ProyectoEnergiaVista(outputArea); // Pasa el JTextArea a la vista
    }

    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        // El controlador solo se encarga de la creación base en la BD.
        // No necesita saber sobre la confirmación de la GUI.
        int idCreado = proyectoEnergiaFacade.crearProyectoEnergia(nombre, tipoFuente, capacidad);
        return idCreado; // Devuelve el ID para que la GUI pueda usarlo
    }

    // Métodos para mostrar mensajes a través de la vista (opcional, podrías hacerlo directamente en la GUI)
    public void mostrarMensaje(String mensaje) {
        proyectoEnergiaVista.mostrarMensaje(mensaje);
    }
    
    public void mostrarDetallesProyecto(ProyectoEnergia proyecto) {
        proyectoEnergiaVista.mostrarDetallesProyecto(proyecto);
    }
}