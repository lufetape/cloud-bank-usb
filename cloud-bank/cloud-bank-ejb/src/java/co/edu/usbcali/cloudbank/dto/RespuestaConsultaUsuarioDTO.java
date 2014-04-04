/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.dto;

import java.io.Serializable;

/**
 * DTO para entregar la respuesta de la consulta de un usuario
 * 
 * @author Felipe
 */
public class RespuestaConsultaUsuarioDTO implements Serializable {
    
    private UsuarioDTO usuario;
    private EstadoDTO estado;

    /**
     * @return the estado
     */
    public EstadoDTO getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }

    /**
     * @return the usuario
     */
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }
    
}
