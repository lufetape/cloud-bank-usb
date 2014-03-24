/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import co.edu.usbcali.cloudbank.services.ITipoUsuarioService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ObjectKeys;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rexis
 */
@ManagedBean
@ViewScoped
public class TipoUsuarioBacking extends BaseBacking implements Serializable {

    @EJB
    private ITipoUsuarioService tipoUsuarioService;
    private List<TiposUsuarios> tiposUsuariosList;
    private List<TiposUsuarios> filteredtiposUsuariosList;
    private TiposUsuarios selectedTipoUsuario;

    @PostConstruct
    public void init() {        
        listarTodos();
    }

    public void listarTodos() {

        try {
            //Consulta de Tipos de Usuarios:
            tiposUsuariosList = tipoUsuarioService.consultarTodos();
            putValueInSession(ObjectKeys.KEY_TIPOS_USUARIOS, tiposUsuariosList);

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_error_listarTodos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void lanzarCrear() {
        selectedTipoUsuario = new TiposUsuarios();
    }
    
    public void lanzarModificar() {
        if(validarSeleccion()){
            RequestContext.getCurrentInstance().execute("modificarDlg.show()");
        }
    }
    
    public void lanzarEliminar() {
        if(validarSeleccion()){
            RequestContext.getCurrentInstance().execute("eliminarDlg.show()");
        }
    }
    
    public boolean validarSeleccion() {
        if(selectedTipoUsuario == null){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
            return false;
        }
        return true;
    }

    public void crear() {

        try {
            //Se crea el Tipo de Usuario:
            tipoUsuarioService.crear(selectedTipoUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_info_registro_creado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void modificar() {

        try {
            //Se modifica el tipo de usuario:
            tipoUsuarioService.modificar(selectedTipoUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_info_registro_modificado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_error_modificar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminar() {

        try {
            //Se elimina el tipo de usuario
            tipoUsuarioService.eliminar(selectedTipoUsuario);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_info_registro_eliminado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_USUARIO, "label_error_eliminar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the tipoUsuarioService
     */
    public ITipoUsuarioService getTipoUsuarioService() {
        return tipoUsuarioService;
    }

    /**
     * @param tipoUsuarioService the tipoUsuarioService to set
     */
    public void setTipoUsuarioService(ITipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    /**
     * @return the tiposUsuariosList
     */
    public List<TiposUsuarios> getTiposUsuariosList() {
        return tiposUsuariosList;
    }

    /**
     * @param tiposUsuariosList the tiposUsuariosList to set
     */
    public void setTiposUsuariosList(List<TiposUsuarios> tiposUsuariosList) {
        this.tiposUsuariosList = tiposUsuariosList;
    }

    /**
     * @return the filteredtiposUsuariosList
     */
    public List<TiposUsuarios> getFilteredtiposUsuariosList() {
        return filteredtiposUsuariosList;
    }

    /**
     * @param filteredtiposUsuariosList the filteredtiposUsuariosList to set
     */
    public void setFilteredtiposUsuariosList(List<TiposUsuarios> filteredtiposUsuariosList) {
        this.filteredtiposUsuariosList = filteredtiposUsuariosList;
    }

    /**
     * @return the selectedTipoUsuario
     */
    public TiposUsuarios getSelectedTipoUsuario() {
        return selectedTipoUsuario;
    }

    /**
     * @param selectedTipoUsuario the selectedTipoUsuario to set
     */
    public void setSelectedTipoUsuario(TiposUsuarios selectedTipoUsuario) {
        this.selectedTipoUsuario = selectedTipoUsuario;
    }
}
