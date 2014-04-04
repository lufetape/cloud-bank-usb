/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.util.ResourceBundles;
import co.edu.usbcali.i_cloudbank.utils.ObjectKeys;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Clase que maneja el Backing de la sesion del usuario
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class SesionUsuarioBacking extends BaseBacking implements Serializable {

    private Usuarios usuario;

    @PostConstruct
    public void init() {
        usuario = ((Usuarios) getValueFromSession(ObjectKeys.KEY_USUARIO_SESSION));
    }

    public void cerrarSesion() {
        removeValueFromSession(ObjectKeys.KEY_USUARIO_SESSION);
        try {
            invalidarSession();
        } catch (Exception ex) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_cerrando_sesion"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the usuario
     */
    public Usuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

}
