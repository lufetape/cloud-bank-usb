/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.dto;

import java.io.Serializable;

/**
 * DTO para manejar información de usuarios del sistema
 * 
 * @author Felipe
 */
public class UsuarioDTO implements Serializable {
   private long identificacion;
   private TipoUsuarioDTO tipoUsuario;
   private String nombre;
   private String login;

    /**
     * @return the identificacion
     */
    public long getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the tipoUsuario
     */
    public TipoUsuarioDTO getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TipoUsuarioDTO tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
}
