package controlador;

import modelo.ProyectoEnergia;
import modelo.ProyectoEnergiaFacade;
import vista.ProyectoEnergiaVista; // Asumo que tienes esta clase, aunque no me la pasaste completamente

public class ProyectoEnergiaControlador {

    private ProyectoEnergiaFacade proyectoEnergiaFacade;
    private ProyectoEnergiaVista proyectoEnergiaVista; // Debes tener esta clase en tu proyecto

    public ProyectoEnergiaControlador() {
        this.proyectoEnergiaFacade = new ProyectoEnergiaFacade();
        // Asegúrate de que esta clase exista y tenga los métodos mostrarMensaje y mostrarDetallesProyecto
        this.proyectoEnergiaVista = new ProyectoEnergiaVista();
    }

    public void crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        int idCreado = proyectoEnergiaFacade.crearProyectoEnergia(nombre, tipoFuente, capacidad);
        if (idCreado > 0) {
            proyectoEnergiaVista.mostrarMensaje("Proyecto creado con ID: " + idCreado);
        } else {
            proyectoEnergiaVista.mostrarMensaje("Error al crear el proyecto.");
        }
    }

    public void mostrarDetallesProyecto(int id, String tipoFuente) {
        ProyectoEnergia proyecto = proyectoEnergiaFacade.obtenerProyectoEnergia(id, tipoFuente);
        proyectoEnergiaVista.mostrarDetallesProyecto(proyecto);
    }

    public void actualizarProyectoEnergia(int id, String nombre, String tipoFuente, double capacidad) {
        int filasAfectadas = proyectoEnergiaFacade.actualizarProyectoEnergia(id, nombre, tipoFuente, capacidad);
        if (filasAfectadas > 0) {
            proyectoEnergiaVista.mostrarMensaje("Proyecto actualizado.");
        } else {
            proyectoEnergiaVista.mostrarMensaje("Error al actualizar el proyecto.");
        }
    }

    public void eliminarProyectoEnergia(int id, String tipoFuente) {
        int filasAfectadas = proyectoEnergiaFacade.eliminarProyectoEnergia(id, tipoFuente);
        if (filasAfectadas > 0) {
            proyectoEnergiaVista.mostrarMensaje("Proyecto eliminado.");
        } else {
            proyectoEnergiaVista.mostrarMensaje("Error al eliminar el proyecto.");
        }
    }
}