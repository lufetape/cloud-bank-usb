/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.backing;

import co.edu.usbcali.e_cloudbank.dto.UsuarioSesionDTO;
import co.edu.usbcali.e_cloudbank.filters.FilterSessionCloudBankPortal;
import co.edu.usbcali.e_cloudbank.services.IUsuarioService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.utils.ObjectKeys;
import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import co.edu.usbcali.e_cloudbank.wsclient.EstadoDTO;
import co.edu.usbcali.e_cloudbank.wsclient.UsuarioDTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;

/**
 *
 * @author rexis
 */
@ManagedBean
@ViewScoped
public class AccountBacking extends BaseBacking implements Serializable {

    @EJB
    private IUsuarioService usuarioService;

    @ManagedProperty(value = "#{loginBacking}")
    private LoginBacking loginBacking;

    private Long identificacion;
    
    private static final Long TIPO_USUARIO_CLIENTE = 30L;

    @PostConstruct
    public void init() {

        if (loginBacking.getManager() == null) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_verificar"),
                    FacesMessage.SEVERITY_ERROR);
            return;
        }

        try {
            verify();
        } catch (DiscoveryException | MessageException | AssociationException e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_verificar"),
                    FacesMessage.SEVERITY_ERROR);
            try {
                redirect(FilterSessionCloudBankPortal.URL_LOGIN);
                return;
            } catch (IOException ioe) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_direccionar"),
                        FacesMessage.SEVERITY_ERROR);
                return;
            }
        }

        if (loginBacking.getOpenIdEmail() == null || loginBacking.getOpenIdEmail().trim().equals("")) {
            try {
                redirect(FilterSessionCloudBankPortal.URL_LOGIN);
                return;
            } catch (IOException ioe) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_direccionar"),
                        FacesMessage.SEVERITY_ERROR);
                return;
            }
        }

        verificarUsuario();
    }

    public void verificarUsuario() {
        try {

            UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorId(loginBacking.getOpenIdEmail()).getUsuario();

            if (usuarioDTO != null) {

                autenticarAndGo(usuarioDTO);
            }

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_verificar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void agregarCuenta() {
        try {
            
            EstadoDTO estadoCrearCuenta = usuarioService.agregarUsuario(identificacion, TIPO_USUARIO_CLIENTE, loginBacking.getFullName(), loginBacking.getOpenIdEmail());

            //Traemos nuevamente todo el usuario para conformar la sesion:
            UsuarioDTO usuarioDTO = usuarioService.consultarUsuarioPorId(loginBacking.getOpenIdEmail()).getUsuario();

            if (estadoCrearCuenta.isExitoso()) {

                autenticarAndGo(usuarioDTO);
            }

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    private void autenticarAndGo(UsuarioDTO usuarioDTO) {

        //Hidratando usuario de sesion
        UsuarioSesionDTO usuarioSesionDTO = new UsuarioSesionDTO();
        usuarioSesionDTO.setIdentificacion(usuarioDTO.getIdentificacion());
        usuarioSesionDTO.setLogin(usuarioDTO.getLogin());
        usuarioSesionDTO.setNombre(usuarioDTO.getNombre());
        usuarioSesionDTO.setTipoUsuario(usuarioDTO.getTipoUsuario());

        //1. ingresando usuario a sesion:
        removeValueFromSession(ObjectKeys.KEY_USUARIO_SESSION);
        putValueInSession(ObjectKeys.KEY_USUARIO_SESSION, usuarioSesionDTO);

        //2. Redireccionando Aplicativo:
        try {
            redirect("../app/inicio.xhtml");
        } catch (IOException ioe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.ACCOUNT, "label_error_direccionar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    private void verify() throws MessageException, DiscoveryException, AssociationException {
        ExternalContext context = javax.faces.context.FacesContext
                .getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        loginBacking.setValidatedId(verifyResponse(request));
    }

    /**
     * Set the class members with date from the authentication response. Extract
     * the parameters from the authentication response (which comes in as a HTTP
     * request from the OpenID provider). Verify the response, examine the
     * verification result and extract the verified identifier.
     *
     * @param httpReq httpRequest
     * @return users identifier.
     */
    private String verifyResponse(HttpServletRequest httpReq) throws MessageException, DiscoveryException, AssociationException {

        ParameterList response
                = new ParameterList(httpReq.getParameterMap());

        StringBuffer receivingURL = httpReq.getRequestURL();
        String queryString = httpReq.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            receivingURL.append("?").append(httpReq.getQueryString());
        }

        VerificationResult verification = loginBacking.getManager().verify(
                receivingURL.toString(),
                response, loginBacking.getDiscovered());

        Identifier verified = verification.getVerifiedId();
        if (verified != null) {
            AuthSuccess authSuccess
                    = (AuthSuccess) verification.getAuthResponse();

            if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
                FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

                List emails = fetchResp.getAttributeValues("email");
                loginBacking.setOpenIdEmail((String) emails.get(0));
                loginBacking.setFullName(fetchResp.getAttributeValue("first_name")+" "+
                        fetchResp.getAttributeValue("last_name"));
            }
            return verified.getIdentifier();
        }
        return null;
    }

    /**
     * @return the loginBacking
     */
    public LoginBacking getLoginBacking() {
        return loginBacking;
    }

    /**
     * @param loginBacking the loginBacking to set
     */
    public void setLoginBacking(LoginBacking loginBacking) {
        this.loginBacking = loginBacking;
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
     * @return the identificacion
     */
    public Long getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }
}
