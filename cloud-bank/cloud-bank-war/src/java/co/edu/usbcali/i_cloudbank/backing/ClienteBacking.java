/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.IConsignacionService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.services.IRetiroService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
 * Clase que representa el Managed Bean de la vista de Clientes
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class ClienteBacking extends BaseBacking implements Serializable {

    @EJB
    private ICuentaService cuentaService;
    @EJB
    private IClienteService clienteService;
    @EJB
    private IConsignacionService consignacionService;
    @EJB
    private IRetiroService retiroService;
    private List<Clientes> clientesList;
    private List<Clientes> filteredClientesList;
    private List<Cuentas> filteredCuentasList;
    private boolean renderedListaClientes;
    private boolean renderedCliente;
    private Clientes selectedCliente;
    private Cuentas selectedCuenta;
    private SelectItem[] tiposDocumentosOptions;
    @ManagedProperty(value = "#{tipoDocumentoBacking}")
    private TipoDocumentoBacking tipoDocumentoBacking;

    /**
     * Variables para hacer filtrado sin restricciones
     */
    private Long identificacion;
    private TiposDocumentos tipoDocumento;
    private String nombre;
    private Date dateInicial;
    private Date dateFinal;

    /**
     * Método que ejecuta operaciones iniciales para el ManagedBean
     */
    @PostConstruct
    public void initialize() {
        resetFiltros();
        listarTodos();
        prepararTiposDocumentos();
    }

    /**
     * Método que lista todos los clientes
     */
    public void listarTodos() {
        try {
            renderizarListaClientes();
            clientesList = getClienteService().consultarTodos();
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_listarTodos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void prepararTiposDocumentos() {
        //Llenando opciones de tipos de documentos para el filtro de la tabla de clientes
        tipoDocumentoBacking.listarTodos();
        tiposDocumentosOptions = new SelectItem[tipoDocumentoBacking.getTiposDocumentosList().size() + 1];
        tiposDocumentosOptions[0] = new SelectItem("", obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_seleccione_todos"));
        int i = 0;
        for (TiposDocumentos tDoc : tipoDocumentoBacking.getTiposDocumentosList()) {
            tiposDocumentosOptions[i + 1] = new SelectItem(tDoc.getTdocCodigo(), tDoc.getTdocNombre());
            i++;
        }
    }

    public void listarPorFiltros() {

        try {
            //Consulta de Clientes por filtros:
            clientesList = clienteService.consultarPorFiltros(identificacion, tipoDocumento != null ? tipoDocumento.getTdocCodigo() : null, nombre);

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_listarPorFiltros"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetFiltros() {
        setIdentificacion(null);
        setTipoDocumento(new TiposDocumentos());
        setNombre(null);
        setDateInicial(null);
        setDateFinal(null);
    }

    public void lanzarVer() {
        if (validarSeleccion()) {
            RequestContext.getCurrentInstance().execute("verDlg.show()");
        }
    }

    public void lanzarCrear() {
        selectedCliente = new Clientes();
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

    /**
     * Método que permite ir a la seccion de productos
     */
    public void lanzarVerProductos() {
        if (validarSeleccion()) {
            try {
                selectedCliente = clienteService.consultarPorId(selectedCliente.getCliId());
                selectedCliente.setCuentasCollection(cuentaService.consultarPorCliente(selectedCliente.getCliId()));
                if (selectedCliente != null) {
                    renderizarCliente();
                } else {
                    this.mostrarMensaje(null,
                            obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                            obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_warning_noEncontrado"),
                            FacesMessage.SEVERITY_WARN);
                }
            } catch (CloudBankException cbe) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                        cbe.getMessage(),
                        FacesMessage.SEVERITY_WARN);
            } catch (Exception e) {
                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_listarPorId"),
                        FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public boolean validarSeleccion() {
        if (selectedCliente == null) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
            return false;
        }
        return true;
    }

    public void crear() {

        try {
            //Se crea el Cliente:
            clienteService.crear(selectedCliente);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_info_registro_creado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void modificar() {

        try {
            //Se modifica el Cliente:
            clienteService.modificar(selectedCliente);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_info_registro_modificado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_modificar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void eliminar() {

        try {
            //Se elimina el Cliente
            clienteService.eliminar(selectedCliente);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_info_registro_eliminado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "label_error_eliminar"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void lanzarRetirarCuenta() {
        if (validarSeleccionCuenta()) {
            RequestContext.getCurrentInstance().execute("retirarCuentaDlg.show()");
        }
    }

    public void lanzarDesactivarCuenta() {
        if (validarSeleccionCuenta()) {
            RequestContext.getCurrentInstance().execute("desactivarCuentaDlg.show()");
        }
    }

    public void lanzarActivarCuenta() {
        if (validarSeleccionCuenta()) {
            RequestContext.getCurrentInstance().execute("activarCuentaDlg.show()");
        }
    }

    public void lanzarVerMovimientosCuenta() {
        if (validarSeleccionCuenta()) {

            RequestContext.getCurrentInstance().execute("movimientosDlg.show()");
            
            //Ultimos 30 dias
            Calendar fechaIncial = Calendar.getInstance();
            fechaIncial.set(Calendar.DATE, fechaIncial.get(Calendar.DATE) - 30);

            Calendar fechaFinal = Calendar.getInstance();

            dateInicial = fechaIncial.getTime();
            dateFinal = fechaFinal.getTime();
            filtrarMovimientosCuenta();
        }
    }

    public void filtrarMovimientosCuenta() {
        try {            
            //Consignaciones
            selectedCuenta.setConsignacionesCollection(consignacionService.consultarPorFiltros(selectedCuenta.getCueNumero(), dateInicial, dateFinal));
            //Retiros            
            selectedCuenta.setRetirosCollection(retiroService.consultarPorFiltros(selectedCuenta.getCueNumero(), dateInicial, dateFinal));

        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_listarMovimientos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void agregarCuenta() {

        try {
            //Se agrega la cuenta del cliente:
            Cuentas cuenta = new Cuentas();
            cuenta.setCliId(selectedCliente);
            cuentaService.crear(cuenta);
            selectedCliente.setCuentasCollection(cuentaService.consultarPorCliente(selectedCliente.getCliId()));

            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_info_registro_creado"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_crear"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retirarCuenta() {
        if (validarSeleccionCuenta()) {
            try {
                //Se retira la cuenta del cliente:
                cuentaService.retirar(selectedCuenta);

                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_info_registro_eliminado"),
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
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_eliminar"),
                        FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public void desactivarCuenta() {
        if (validarSeleccionCuenta()) {
            try {
                //Se desactiva la cuenta del cliente:
                cuentaService.desactivar(selectedCuenta);

                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_info_registro_desactivado"),
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
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_desactivar"),
                        FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public void activarCuenta() {
        if (validarSeleccionCuenta()) {
            try {
                //Se activa la cuenta del cliente:
                cuentaService.activar(selectedCuenta);

                this.mostrarMensaje(null,
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_info_registro_activado"),
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
                        obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_activar"),
                        FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public boolean validarSeleccionCuenta() {
        if (selectedCuenta == null) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
            return false;
        }
        return true;
    }

    public void renderizarListaClientes() {
        //Cuando se renderiza la lista de clientes, se quita la renderizacion de los otros componentes
        setRenderedListaClientes(true);
        setRenderedCliente(false);

    }

    public void renderizarCliente() {
        //Cuando se renderiza el cliente, se quita la renderizacion de los otros componentes
        setRenderedListaClientes(false);
        setRenderedCliente(true);
    }

    /**
     * @return the clienteService
     */
    public IClienteService getClienteService() {
        return clienteService;
    }

    /**
     * @param clienteService the clienteService to set
     */
    public void setClienteService(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * @return the clientesList
     */
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    /**
     * @param clientesList the clientesList to set
     */
    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    /**
     * @return the filteredClientesList
     */
    public List<Clientes> getFilteredClientesList() {
        return filteredClientesList;
    }

    /**
     * @param filteredClientesList the filteredClientesList to set
     */
    public void setFilteredClientesList(List<Clientes> filteredClientesList) {
        this.filteredClientesList = filteredClientesList;
    }

    /**
     * @return the filteredCuentasList
     */
    public List<Cuentas> getFilteredCuentasList() {
        return filteredCuentasList;
    }

    /**
     * @param filteredCuentasList the filteredCuentasList to set
     */
    public void setFilteredCuentasList(List<Cuentas> filteredCuentasList) {
        this.filteredCuentasList = filteredCuentasList;
    }

    /**
     * @return the renderedListaClientes
     */
    public boolean isRenderedListaClientes() {
        return renderedListaClientes;
    }

    /**
     * @param renderedListaClientes the renderedListaClientes to set
     */
    public void setRenderedListaClientes(boolean renderedListaClientes) {
        this.renderedListaClientes = renderedListaClientes;
    }

    /**
     * @return the renderedCliente
     */
    public boolean isRenderedCliente() {
        return renderedCliente;
    }

    /**
     * @param renderedCliente the renderedCliente to set
     */
    public void setRenderedCliente(boolean renderedCliente) {
        this.renderedCliente = renderedCliente;
    }

    /**
     * @return the selectedCliente
     */
    public Clientes getSelectedCliente() {
        return selectedCliente;
    }

    /**
     * @param selectedCliente the selectedCliente to set
     */
    public void setSelectedCliente(Clientes selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    /**
     * @return the tiposDocumentosOptions
     */
    public SelectItem[] getTiposDocumentosOptions() {
        return tiposDocumentosOptions;
    }

    /**
     * @param tiposDocumentosOptions the tiposDocumentosOptions to set
     */
    public void setTiposDocumentosOptions(SelectItem[] tiposDocumentosOptions) {
        this.tiposDocumentosOptions = tiposDocumentosOptions;
    }

    /**
     * @return the tipoDocumentoBacking
     */
    public TipoDocumentoBacking getTipoDocumentoBacking() {
        return tipoDocumentoBacking;
    }

    /**
     * @param tipoDocumentoBacking the tipoDocumentoBacking to set
     */
    public void setTipoDocumentoBacking(TipoDocumentoBacking tipoDocumentoBacking) {
        this.tipoDocumentoBacking = tipoDocumentoBacking;
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

    /**
     * @return the tipoDocumento
     */
    public TiposDocumentos getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(TiposDocumentos tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
     * @return the selectedCuenta
     */
    public Cuentas getSelectedCuenta() {
        return selectedCuenta;
    }

    /**
     * @param selectedCuenta the selectedCuenta to set
     */
    public void setSelectedCuenta(Cuentas selectedCuenta) {
        this.selectedCuenta = selectedCuenta;
    }

    /**
     * @return the cuentaService
     */
    public ICuentaService getCuentaService() {
        return cuentaService;
    }

    /**
     * @param cuentaService the cuentaService to set
     */
    public void setCuentaService(ICuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    /**
     * @return the consignacionService
     */
    public IConsignacionService getConsignacionService() {
        return consignacionService;
    }

    /**
     * @param consignacionService the consignacionService to set
     */
    public void setConsignacionService(IConsignacionService consignacionService) {
        this.consignacionService = consignacionService;
    }

    /**
     * @return the retiroService
     */
    public IRetiroService getRetiroService() {
        return retiroService;
    }

    /**
     * @param retiroService the retiroService to set
     */
    public void setRetiroService(IRetiroService retiroService) {
        this.retiroService = retiroService;
    }

    /**
     * @return the dateInicial
     */
    public Date getDateInicial() {
        return dateInicial;
    }

    /**
     * @param dateInicial the dateInicial to set
     */
    public void setDateInicial(Date dateInicial) {
        this.dateInicial = dateInicial;
    }

    /**
     * @return the dateFinal
     */
    public Date getDateFinal() {
        return dateFinal;
    }

    /**
     * @param dateFinal the dateFinal to set
     */
    public void setDateFinal(Date dateFinal) {
        this.dateFinal = dateFinal;
    }
}
