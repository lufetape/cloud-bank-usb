package co.edu.usbcali.i_cloudbank.filters;

import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.i_cloudbank.utils.ObjectKeys;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Esta clase permite manejar el filtrado de peticiones en busca de que el
 * usuario se encuentre logueado y tenga los permisos correctos
 *
 * @author Luis Felipe Tabares
 */
public final class FilterSessionCloudBank implements Filter {

    private String URL_LOGIN;

    private static final String BASE_PREFIX = "/app";
    private static final String BASE_SUFFIX = ".xhtml";

    private static final String[] OPCIONES_ADMINISTRADOR = {"/administracion/usuarios/principal",
        "/administracion/tiposUsuarios/principal"};

    private static final String[] OPCIONES_CAJERO = {"/transacciones/consignar",
        "/transacciones/retirar",
        "/transacciones/transferir"};

    private static final String[] OPCIONES_ASESOR = {"/administracion/clientes/principal",
        "/administracion/tiposDocumentos/principal"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        URL_LOGIN = filterConfig.getInitParameter("loginURL");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        boolean ajax = "partial/ajax".equals(request.getHeader("Faces-Request"));

        //Validacion de Sesion: 
        Usuarios usuario = (Usuarios) session.getAttribute(ObjectKeys.KEY_USUARIO_SESSION);

        //1. Verifica que el usuario tenga la sesion activa:   
        if (usuario == null) {
            redirect(ajax, response, response.encodeRedirectURL(URL_LOGIN));
            return;
        }

        //2. Verifica que el usuario tenga permisos segun su tipo
        if(!isAuthorized(request.getServletPath(), usuario)){
            redirect(ajax, response, response.encodeRedirectURL(URL_LOGIN));
            return;
        }
        
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
    
    private boolean isAuthorized(String path, Usuarios usuario){
        
        if(path.equals(BASE_PREFIX+"/inicio"+BASE_SUFFIX)){
            return true;
        }
        
        String[] verificationPaths;
        
        //Se configuran los paths de donde se verificara
        switch(usuario.getTusuCodigo().getTusuCodigo().intValue()){
            case 10:
                verificationPaths = OPCIONES_CAJERO;
                break;
            case 20:
                verificationPaths = OPCIONES_ASESOR;
                break;
            case 40:
                verificationPaths = OPCIONES_ADMINISTRADOR;
                break;
            case 50:
                verificationPaths = new String[0]; //Todos los permisos
                break;
            default:
                verificationPaths = null; //Ningun permiso
        }
        
        //Si no hay paths a verificar (NULL), el tipo de usuario no tiene permisos
        if(verificationPaths == null){
            return false;
        }
        
        //Si los paths no son nulos pero no tienen elementos, el tipo de usuario tiene todos los permisos
        if(verificationPaths.length == 0){
            return true;
        }
        
        //Si el size es > 0, se entra a verificar si tiene el permiso
        if(verificationPaths.length > 0){
            for(String permission : verificationPaths){
                if((BASE_PREFIX+permission+BASE_SUFFIX).trim().equals(path.trim())){
                    return true;
                }
            }
        }
        
        return false;
    }

    private void redirect(boolean ajax, HttpServletResponse response, String url) throws IOException {

        if (ajax) {
            response.setContentType("text/xml");
            response.getWriter()
                    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                    .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>",
                            url);
        } else {
            response.sendRedirect(url);
        }
    }
}
