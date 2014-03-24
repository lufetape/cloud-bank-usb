/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.cloudbank.services.ITipoDocumentoService;
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
public class TipoDocumentoBacking extends BaseBacking implements Serializable {

    @EJB
    private ITipoDocumentoService tipoDocumentoService;
    private List<TiposDocumentos> tiposDocumentosList;
    private List<TiposDocumentos> filteredtiposDocumentosList;
    private TiposDocumentos selectedTipoDocumento;

    @PostConstruct
    public void init() {        
        listarTodos();
    }

    public void listarTodos() {

        try {
            //Consulta de Tipos de Documentos:
            tiposDocumentosList = tipoDocumentoService.consultarTodos();
            putValueInSession(ObjectKeys.KEY_TIPOS_DOCUMENTOS, tiposDocumentosList);

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_error_listarTodos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void lanzarCrear() {
        selectedTipoDocumento = new TiposDocumentos();
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
        if(selectedTipoDocumento == null){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
            return false;
        }
        return true;
    }

    public void crear() {

        try {
            //Se crea el Tipo de Documento:
            tipoDocumentoService.crear(selectedTipoDocumento);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_info_registro_creado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void modificar() {

        try {
            //Se modifica el tipo de documento:
            tipoDocumentoService.modificar(selectedTipoDocumento);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_info_registro_modificado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_error_modificar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminar() {

        try {
            //Se elimina el tipo de documento
            tipoDocumentoService.eliminar(selectedTipoDocumento);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_info_registro_eliminado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TIPO_DOCUMENTO, "label_error_eliminar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * @return the tipoDocumentoService
     */
    public ITipoDocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    /**
     * @param tipoDocumentoService the tipoDocumentoService to set
     */
    public void setTipoDocumentoService(ITipoDocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    /**
     * @return the tiposDocumentosList
     */
    public List<TiposDocumentos> getTiposDocumentosList() {
        return tiposDocumentosList;
    }

    /**
     * @param tiposDocumentosList the tiposDocumentosList to set
     */
    public void setTiposDocumentosList(List<TiposDocumentos> tiposDocumentosList) {
        this.tiposDocumentosList = tiposDocumentosList;
    }

    /**
     * @return the filteredtiposDocumentosList
     */
    public List<TiposDocumentos> getFilteredtiposDocumentosList() {
        return filteredtiposDocumentosList;
    }

    /**
     * @param filteredtiposDocumentosList the filteredtiposDocumentosList to set
     */
    public void setFilteredtiposDocumentosList(List<TiposDocumentos> filteredtiposDocumentosList) {
        this.filteredtiposDocumentosList = filteredtiposDocumentosList;
    }

    /**
     * @return the selectedTipoDocumento
     */
    public TiposDocumentos getSelectedTipoDocumento() {
        return selectedTipoDocumento;
    }

    /**
     * @param selectedTipoDocumento the selectedTipoDocumento to set
     */
    public void setSelectedTipoDocumento(TiposDocumentos selectedTipoDocumento) {
        this.selectedTipoDocumento = selectedTipoDocumento;
    }
}
