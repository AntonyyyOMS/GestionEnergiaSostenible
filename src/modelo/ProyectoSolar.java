/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author MI PC
 */
// Clases concretas para cada tipo de proyecto
class ProyectoSolar implements TipoProyectoEnergia {
    @Override
    public ProyectoEnergia crearProyecto() {
        return new ProyectoEnergia("Solar");
    }
}