/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.backing;

import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;

/**
 *
 * @author rexis
 */
@ManagedBean
@SessionScoped
public class LoginBacking extends BaseBacking implements Serializable {

    private String userSuppliedId; //Users OpenID URL
    private String validatedId;
    private String openIdEmail;
    private String fullName;
    private ConsumerManager manager;
    private DiscoveryInformation discovered;

    public void loginCustom(String openid) {
        userSuppliedId = openid;
        login();
    }

    public void loginOpenId() {
        login();
    }

    public void login() {
        validatedId = null;
        manager = new ConsumerManager();
        try {
            String returnToUrl = returnToUrl(obtenerMensaje(ResourceBundles.RB_MENSAJES.OPEN_ID, "returnURL"));

            try {
                String url = authRequest(returnToUrl);
                if (url != null) {
                    redirect(url);
                }
            } catch (IOException ioe) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.LOGIN, "label_error_direccionar"),
                        FacesMessage.SEVERITY_ERROR);
            }
        } catch (ConsumerException | DiscoveryException | MessageException e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.LOGIN, "label_error_autenticar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Create the current url and add another url path fragment on it. Obtain
     * from the current context the url and add another url path fragment at the
     * end
     *
     * @param urlExtension f.e. /nextside.xhtml
     * @return the hole url including the new fragment
     */
    private String returnToUrl(String urlExtension) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String returnToUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                + context.getApplication().getViewHandler().getActionURL(context, urlExtension);
        return returnToUrl;
    }

    /**
     * Create an authentication request. It performs a discovery on the
     * user-supplied identifier. Attempt it to associate with the OpenID
     * provider and retrieve one service endpoint for authentication. It adds
     * some attributes for exchange on the AuthRequest. A List of all possible
     * attributes can be found on @see http://www.axschema.org/types/
     *
     * @param returnToUrl
     * @return the URL where the message should be sent
     * @throws IOException
     */
    private String authRequest(String returnToUrl) throws IOException, DiscoveryException, MessageException, ConsumerException {

        List discoveries = manager.discover(userSuppliedId);
        discovered = manager.associate(discoveries);
        AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

        FetchRequest fetch = FetchRequest.createFetchRequest();
        if (userSuppliedId.contains("myopenid")) {
            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);            
            fetch.addAttribute("first_name", "http://schema.openid.net/namePerson/first", true);
            fetch.addAttribute("middle_name", "http://schema.openid.net/namePerson/middle", true);
            fetch.addAttribute("last_name", "http://schema.openid.net/namePerson/last", true);
        } else {
            fetch.addAttribute("email", "http://axschema.org/contact/email", true);
            fetch.addAttribute("first_name", "http://axschema.org/namePerson/first", true);
            fetch.addAttribute("middle_name", "http://axschema.org/namePerson/middle", true);
            fetch.addAttribute("last_name", "http://axschema.org/namePerson/last", true);
        }

        authReq.addExtension(fetch);
        return authReq.getDestinationUrl(true);
    }

    /**
     * @return the userSuppliedId
     */
    public String getUserSuppliedId() {
        return userSuppliedId;
    }

    /**
     * @param userSuppliedId the userSuppliedId to set
     */
    public void setUserSuppliedId(String userSuppliedId) {
        this.userSuppliedId = userSuppliedId;
    }

    /**
     * @return the validatedId
     */
    public String getValidatedId() {
        return validatedId;
    }

    /**
     * @param validatedId the validatedId to set
     */
    public void setValidatedId(String validatedId) {
        this.validatedId = validatedId;
    }

    /**
     * @return the openIdEmail
     */
    public String getOpenIdEmail() {
        return openIdEmail;
    }

    /**
     * @param openIdEmail the openIdEmail to set
     */
    public void setOpenIdEmail(String openIdEmail) {
        this.openIdEmail = openIdEmail;
    }

    /**
     * @return the manager
     */
    public ConsumerManager getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(ConsumerManager manager) {
        this.manager = manager;
    }

    /**
     * @return the discovered
     */
    public DiscoveryInformation getDiscovered() {
        return discovered;
    }

    /**
     * @param discovered the discovered to set
     */
    public void setDiscovered(DiscoveryInformation discovered) {
        this.discovered = discovered;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
