package co.edu.usbcali.e_cloudbank.backing;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase que maneja los métodos base de Backing
 * 
 * @author Felipe
 */
public class BaseBacking {
       
    public BaseBacking() {       
    }
    
    /**
     * Método que devuelve un valor en el contexto de aplicación
     * @param llave
     * @return
     */
    protected Object getValueFromApplication(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         
         return (httpServletRequest.getServletContext().getAttribute(llave));
    }
    
    /**
     * Remueve el atributo del contexto de aplicación
     * @param llave
     */
    protected void removeValueFromApplication(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         httpServletRequest.getServletContext().removeAttribute(llave);
    }  
 
    /**
     * 
     * Método que coloca valores en la sesión
     * @param llave
     * @param valor
     */
    protected void putValueInSession(String llave, Object valor)
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        httpServletRequest.getSession().setAttribute(llave, valor);
    }
       
    /**
     * Metodo que devuelve un valor de la sesión
     * @param llave
     * @return
     */
    protected Object getValueFromSession(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         
         return (httpServletRequest.getSession().getAttribute(llave));
    }
    
    /**
     * Remueve el atributo de la sesión
     * @param llave
     */
    protected void removeValueFromSession(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         httpServletRequest.getSession().removeAttribute(llave);
    }
    
    /**
     * Obtiene un valor del request
     * @param llave
     * @return 
     */
    protected Object getValueFromRequest(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         return (httpServletRequest.getAttribute(llave));
    }
    
    /**
     * Remueve el atributo del request
     * @param llave
     */
    protected void removeValueFromRequest(String llave)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         httpServletRequest.removeAttribute(llave);
    }
    
    /**
     * Método que coloca un valor en el contexto de aplicación
     * @param llave
     * @param valor
     */
    protected void putValueInApplication(String llave, Object valor)
    {
         HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         httpServletRequest.getServletContext().setAttribute(llave, valor);
    }
    
    /**
     * Método que obtiene un mensaje de los archivos de recursos con el LOCALE del Contexto
     * @param archivo
     * @param mensaje
     * @return
     */
    protected String obtenerMensaje(Enum archivo, String mensaje)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getViewRoot().getLocale(); 
        ResourceBundle bundle = ResourceBundle.getBundle(archivo.toString(), locale); 
        
        return (bundle.getString(mensaje));
    } 
    
    /**
     * Método que muestra un mensaje en la página con resumen y severidad
     * @param componente
     * @param resumen
     * @param mensaje
     * @param severidad
     */
    protected void mostrarMensaje(String componente, String resumen, String mensaje, Severity severidad)
    {       
        FacesContext context = FacesContext.getCurrentInstance();        
        context.addMessage(componente,new FacesMessage(severidad, resumen, mensaje));        
    }
    
    /*
     * Método que realiza un redirect a una URL
     */
    protected void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
    
    /**
     * Método que inactiva la session
     * @throws Exception
     */
    protected void invalidarSession() throws Exception
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = httpServletRequest.getSession();
        if(session != null && !session.isNew()) {
            session.setMaxInactiveInterval(1);
        }
    }
    
    /**
     * Asigna un valor al request
     * @param llave
     * @param valor
     */
    protected void putValueInRequest(String llave, Object valor)
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        httpServletRequest.setAttribute(llave, valor);
    }
}