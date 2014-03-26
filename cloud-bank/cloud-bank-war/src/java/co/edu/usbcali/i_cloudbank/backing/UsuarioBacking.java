/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.services.IUsuarioService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rexis
 */
@ManagedBean
@ViewScoped
public class UsuarioBacking extends BaseBacking implements Serializable {

    @EJB
    private IUsuarioService usuarioService;
    private List<Usuarios> usuariosList;
    private List<Usuarios> filteredUsuariosList;
    private Usuarios selectedUsuario;
    private SelectItem[] tiposUsuariosOptions;
    @ManagedProperty(value = "#{tipoUsuarioBacking}")
    private TipoUsuarioBacking tipoUsuarioBacking;
    private boolean cambiarClave;
    
    /**
     * Variables para hacer filtrado sin restricciones
     */
    private Long codigo;
    private TiposUsuarios tipoUsuario;
    private String login;
    private String nombre;

    @PostConstruct
    public void init() {
        resetFiltros();
        listarTodos();
        prepararTiposUsuarios();
    }

    public void listarTodos() {

        try {
            //Consulta de Usuarios:
            usuariosList = usuarioService.consultarTodos();

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_error_listarTodos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void prepararTiposUsuarios() {
        //Llenando opciones de tipos de usuarios para el filtro de la tabla
        tipoUsuarioBacking.listarTodos();
        tiposUsuariosOptions = new SelectItem[tipoUsuarioBacking.getTiposUsuariosList().size() + 1];
        tiposUsuariosOptions[0] = new SelectItem("", obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_seleccione_todos"));
        int i = 0;
        for (TiposUsuarios tUsu : tipoUsuarioBacking.getTiposUsuariosList()) {
            tiposUsuariosOptions[i + 1] = new SelectItem(tUsu.getTusuCodigo(), tUsu.getTusuNombre());
            i++;
        }
    }

    public void listarPorFiltros() {

        try {
            //Consulta de Usuarios por filtros:
            usuariosList = usuarioService.consultarPorFiltros(codigo, tipoUsuario != null ? tipoUsuario.getTusuCodigo() : null, login, nombre);

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_error_listarPorFiltros"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void resetFiltros() {
        setCodigo(null);
        setTipoUsuario(new TiposUsuarios());
        setLogin(null);
        setNombre(null);
    }

    public void lanzarCrear() {
        selectedUsuario = new Usuarios();
    }

    public void lanzarModificar() {
        if (validarSeleccion()) {
            RequestContext.getCurrentInstance().execute("modificarDlg.show()");
        }
    }

    public void lanzarEliminar() {
        if (validarSeleccion()) {
            RequestContext.getCurrentInstance().execute("eliminarDlg.show()");
        }
    }

    public boolean validarSeleccion() {
        if (selectedUsuario == null) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
            return false;
        }
        return true;
    }

    public void crear() {

        try {
            //Se crea el Usuario:
            usuarioService.crear(selectedUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_info_registro_creado"),
                    FacesMessage.SEVERITY_INFO);
            RequestContext.getCurrentInstance().execute("crearDlg.hide()");
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void modificar() {

        try {
            //Se modifica el Usuario:
            usuarioService.modificar(selectedUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_info_registro_modificado"),
                    FacesMessage.SEVERITY_INFO);
            RequestContext.getCurrentInstance().execute("modificarDlg.hide()");
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_error_modificar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminar() {

        try {
            //Se elimina el Usuario
            usuarioService.eliminar(selectedUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_info_registro_eliminado"),
                    FacesMessage.SEVERITY_INFO);
            listarTodos();
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.USUARIO, "label_error_eliminar"),
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
     * @return the usuariosList
     */
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    /**
     * @param usuariosList the usuariosList to set
     */
    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    /**
     * @return the filteredUsuariosList
     */
    public List<Usuarios> getFilteredUsuariosList() {
        return filteredUsuariosList;
    }

    /**
     * @param filteredUsuariosList the filteredUsuariosList to set
     */
    public void setFilteredUsuariosList(List<Usuarios> filteredUsuariosList) {
        this.filteredUsuariosList = filteredUsuariosList;
    }

    /**
     * @return the selectedUsuario
     */
    public Usuarios getSelectedUsuario() {
        return selectedUsuario;
    }

    /**
     * @param selectedUsuario the selectedUsuario to set
     */
    public void setSelectedUsuario(Usuarios selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    /**
     * @return the tiposUsuariosOptions
     */
    public SelectItem[] getTiposUsuariosOptions() {
        return tiposUsuariosOptions;
    }

    /**
     * @param tiposUsuariosOptions the tiposUsuariosOptions to set
     */
    public void setTiposUsuariosOptions(SelectItem[] tiposUsuariosOptions) {
        this.tiposUsuariosOptions = tiposUsuariosOptions;
    }

    /**
     * @return the tipoUsuarioBacking
     */
    public TipoUsuarioBacking getTipoUsuarioBacking() {
        return tipoUsuarioBacking;
    }

    /**
     * @param tipoUsuarioBacking the tipoUsuarioBacking to set
     */
    public void setTipoUsuarioBacking(TipoUsuarioBacking tipoUsuarioBacking) {
        this.tipoUsuarioBacking = tipoUsuarioBacking;
    }

    /**
     * @return the cambiarClave
     */
    public boolean isCambiarClave() {
        return cambiarClave;
    }

    /**
     * @param cambiarClave the cambiarClave to set
     */
    public void setCambiarClave(boolean cambiarClave) {
        this.cambiarClave = cambiarClave;
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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
     * @return the tipoUsuario
     */
    public TiposUsuarios getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * @param tipoUsuario the tipoUsuario to set
     */
    public void setTipoUsuario(TiposUsuarios tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
