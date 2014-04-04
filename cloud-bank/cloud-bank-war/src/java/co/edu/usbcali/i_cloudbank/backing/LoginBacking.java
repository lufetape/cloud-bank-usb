/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.services.IUsuarioService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ObjectKeys;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author rexis
 */
@ManagedBean
@RequestScoped
public class LoginBacking extends BaseBacking implements Serializable {

    @EJB
    private IUsuarioService usuarioService;

    private String login;
    private String clave;

    public void autenticar() {

        try {

            //1. Autenticando Usuario:
            removeValueFromSession(ObjectKeys.KEY_USUARIO_SESSION);
            putValueInSession(ObjectKeys.KEY_USUARIO_SESSION, usuarioService.autenticarUsuario(login, clave));

            //2. Redireccionando Aplicativo:
            try {
                redirect("../app/inicio.xhtml");
            } catch (IOException ioe) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.LOGIN, "label_error_direccionar"),
                        FacesMessage.SEVERITY_ERROR);
            }

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.LOGIN, "label_error_autenticar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the usuarioService
     */
    public IUsuarioService getUsuarioService() {
        return usuarioService;
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
}
