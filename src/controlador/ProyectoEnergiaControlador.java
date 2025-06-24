package controlador;

import modelo.ProyectoEnergia;
import vista.ProyectoEnergiaVista;
import javax.swing.JTextArea;
import java.util.List;

public class ProyectoEnergiaControlador {
    private final ProyectoEnergiaFacade proyectoEnergiaFacade;
    private final ProyectoEnergiaVista proyectoEnergiaVista;

    public ProyectoEnergiaControlador(JTextArea outputArea) {
        this.proyectoEnergiaFacade = new ProyectoEnergiaFacade(); // Ahora usa el Proxy
        this.proyectoEnergiaVista = (outputArea != null) 
            ? new ProyectoEnergiaVista(outputArea) 
            : null;
    }

    // ▄▀▄ Métodos CRUD que ahora usan la Fachada (con Proxy) ▄▀▄
    
    /** Crea un nuevo proyecto de energía (delega en la Fachada → Proxy → DAO Real) */
    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        int idGenerado = proyectoEnergiaFacade.crearProyectoEnergia(nombre, tipoFuente, capacidad);
        
        if (idGenerado != -1) {
            mostrarMensaje("✅ Proyecto creado exitosamente. ID: " + idGenerado);
        } else {
            mostrarMensaje("❌ Error al crear el proyecto");
        }
        return idGenerado;
    }

    /** Obtiene un proyecto por ID (usa caché si está disponible) */
    public ProyectoEnergia leerProyectoEnergia(int id) {
        ProyectoEnergia proyecto = proyectoEnergiaFacade.leerProyectoEnergia(id);
        
        if (proyecto != null) {
            mostrarDetallesProyecto(proyecto);
        } else {
            mostrarMensaje("⚠️ No se encontró el proyecto con ID: " + id);
        }
        return proyecto;
    }

    /** Obtiene todos los proyectos (sin caché) */
    public List<ProyectoEnergia> leerTodosProyectosEnergia() {
        List<ProyectoEnergia> proyectos = proyectoEnergiaFacade.leerTodosProyectosEnergia();
        
        if (proyectos != null && !proyectos.isEmpty()) {
            mostrarMensaje("📋 Total de proyectos: " + proyectos.size());
        } else {
            mostrarMensaje("ℹ️ No hay proyectos registrados");
        }
        return proyectos;
    }

    /** Actualiza un proyecto (actualiza la caché si tiene éxito) */
    public boolean actualizarProyectoEnergia(int id, String nombre, String tipoFuente, double capacidad) {
        boolean exito = proyectoEnergiaFacade.actualizarProyectoEnergia(id, nombre, tipoFuente, capacidad);
        
        if (exito) {
            mostrarMensaje("🔄 Proyecto actualizado correctamente");
        } else {
            mostrarMensaje("⛔ Error al actualizar el proyecto");
        }
        return exito;
    }

    /** Elimina un proyecto (limpia la caché si tiene éxito) */
    public boolean eliminarProyectoEnergia(int id) {
        boolean exito = proyectoEnergiaFacade.eliminarProyectoEnergia(id);
        
        if (exito) {
            mostrarMensaje("🗑️ Proyecto eliminado correctamente");
        } else {
            mostrarMensaje("⛔ Error al eliminar el proyecto");
        }
        return exito;
    }

    // ▄▀▄ Métodos de la Vista (sin cambios) ▄▀▄
    
    public void mostrarMensaje(String mensaje) {
        if (proyectoEnergiaVista != null) {
            proyectoEnergiaVista.mostrarMensaje(mensaje);
        } else {
            System.out.println(mensaje);
        }
    }

    public void mostrarDetallesProyecto(ProyectoEnergia proyecto) {
        if (proyectoEnergiaVista != null) {
            proyectoEnergiaVista.mostrarDetallesProyecto(proyecto);
        } else {
            System.out.println(proyecto);
        }
    }
}