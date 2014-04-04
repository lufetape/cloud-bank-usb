package co.edu.usbcali.e_cloudbank.backing;

import co.edu.usbcali.e_cloudbank.dto.UsuarioSesionDTO;
import co.edu.usbcali.e_cloudbank.utils.ObjectKeys;
import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Clase que maneja el Backing de la sesion del usuario
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class SesionUsuarioBacking extends BaseBacking implements Serializable {

    private UsuarioSesionDTO usuario;

    @PostConstruct
    public void init() {
        usuario = ((UsuarioSesionDTO) getValueFromSession(ObjectKeys.KEY_USUARIO_SESSION));
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
    public UsuarioSesionDTO getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(UsuarioSesionDTO usuario) {
        this.usuario = usuario;
    }

}
