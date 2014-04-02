/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.cloudbank.dto;

import java.io.Serializable;

/**
 * DTO para manejar las respuestas que tendrán los componentes de integración
 *
 * @author Felipe
 */
public class EstadoDTO implements Serializable {

    private boolean exitoso;
    private String descripcion;

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the exitoso
     */
    public boolean isExitoso() {
        return exitoso;
    }

    /**
     * @param exitoso the exitoso to set
     */
    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

}
