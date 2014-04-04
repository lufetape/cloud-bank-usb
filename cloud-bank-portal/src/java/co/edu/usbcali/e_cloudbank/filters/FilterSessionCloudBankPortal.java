package co.edu.usbcali.e_cloudbank.filters;

import co.edu.usbcali.e_cloudbank.dto.UsuarioSesionDTO;
import co.edu.usbcali.e_cloudbank.utils.ObjectKeys;
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
public final class FilterSessionCloudBankPortal implements Filter {

    public static String URL_LOGIN;

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
        UsuarioSesionDTO usuario = (UsuarioSesionDTO) session.getAttribute(ObjectKeys.KEY_USUARIO_SESSION);

        //Verifica que el usuario tenga la sesion activa:   
        if (usuario == null) {
            redirect(ajax, response, response.encodeRedirectURL(URL_LOGIN));
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
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
