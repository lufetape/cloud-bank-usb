/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Clase que representa el Managed Bean de la vista de Clientes
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class ClienteBacking extends BaseBacking implements Serializable {
    
    @EJB
    private ICuentaService cuentaService;
    
    @EJB
    private IClienteService clienteService;
    
    private List<Clientes> clientesList;
    private List<Clientes> filteredClientesList;
    private List<Cuentas> filteredCuentasList;
    private List<Consignaciones> filteredConsignacionesList;
    private List<Retiros> filteredRetirosList;
    private boolean renderedListaClientes;
    private boolean renderedCliente;
    private boolean renderedCuenta;
    private Clientes selectedCliente;
    private Cuentas selectedCuentas;
    private Consignaciones selectedConsignaciones;
    private Retiros selectedRetiros;
    private Clientes filtrosCliente;
    private String palabraClave;

    /**
     * Método que ejecuta operaciones iniciales para el ManagedBean
     */
    @PostConstruct
    public void initialize(){
        listarClientes();
    }
    
    /**
     * Método que lista todos los clientes
     */
    public void listarClientes(){
        try {
            renderizarListaClientes();
            setClientesList(clienteService.consultarTodos());
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch(Exception e){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_error"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "error_listarTodos_cliente"),
                    FacesMessage.SEVERITY_WARN);
        }
    }
    
    /**
     * Método que permite la seleccion de un cliente
     */
    public void seleccionarCliente(){
        try {   
            setSelectedCliente(clienteService.consultarPorId(selectedCliente.getCliId()));
            if(getSelectedCliente() != null){
                renderizarCliente();
            } else {
                this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "warn_cliente_noEncontrado"),
                    FacesMessage.SEVERITY_WARN);
            }
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch(Exception e){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_error"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "error_consultarPorId_cliente"),
                    FacesMessage.SEVERITY_WARN);
        }
    }    
    
    /**
     * Método que permite la seleccion de una cuenta
     */
    public void seleccionarCuenta(){
        try {   
            setSelectedCuentas(cuentaService.consultarPorNumero(selectedCuentas.getCueNumero()));
            if(getSelectedCuentas()!= null){
                renderizarCuenta();
            } else {
                this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "warn_cuenta_noEncontrada"),
                    FacesMessage.SEVERITY_WARN);
            }
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch(Exception e){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_error"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "error_consultarPorNumero_cuenta"),
                    FacesMessage.SEVERITY_WARN);
        }
    }
    
    /**
     * Método que lista clientes por palabra clave
     */
    public void listarPorPalabraClave(){
        try {
            setClientesList(clienteService.consultarPorPalabraClave(getPalabraClave()));
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch(Exception e){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_error"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "error_listarPorPalabraClave_cliente"),
                    FacesMessage.SEVERITY_WARN);
        }
    }
    
    /**
     * Método que lista clientes por diferentes filtros
     */
    public void listarPorFiltros(){
        try {
            setClientesList(clienteService.consultarPorFiltros(filtrosCliente.getCliId(), filtrosCliente.getTdocCodigo().getTdocCodigo(), filtrosCliente.getCliNombre()));
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_warning"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch(Exception e){
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "titulo_error"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CLIENTE, "error_listarPorFiltros_cliente"),
                    FacesMessage.SEVERITY_WARN);
        }
    }
    
    public void renderizarListaClientes(){
        //Cuando se renderiza la lista de clientes, se quita la renderizacion de los otros componentes
        setRenderedListaClientes(true);
        setRenderedCliente(false);
        setRenderedCuenta(false);
        
    }
    
    public void renderizarCliente(){
        //Cuando se renderiza el cliente, se quita la renderizacion de los otros componentes
        setRenderedListaClientes(false);
        setRenderedCliente(true);
        setRenderedCuenta(false);
    }
    
    public void renderizarCuenta(){
        //Cuando se renderiza el cliente, se quita la renderizacion de los otros componentes
        setRenderedListaClientes(false);
        setRenderedCliente(false);
        setRenderedCuenta(true);
    }    
    
    public void crear(){
        
    }
    
    public void modificar(){
        
    }
    
    public void eliminar(){
        
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
     * @return the filtrosCliente
     */
    public Clientes getFiltrosCliente() {
        return filtrosCliente;
    }

    /**
     * @param filtrosCliente the filtrosCliente to set
     */
    public void setFiltrosCliente(Clientes filtrosCliente) {
        this.filtrosCliente = filtrosCliente;
    }

    /**
     * @return the palabraClave
     */
    public String getPalabraClave() {
        return palabraClave;
    }

    /**
     * @param palabraClave the palabraClave to set
     */
    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
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
     * @return the selectedCuentas
     */
    public Cuentas getSelectedCuentas() {
        return selectedCuentas;
    }

    /**
     * @param selectedCuentas the selectedCuentas to set
     */
    public void setSelectedCuentas(Cuentas selectedCuentas) {
        this.selectedCuentas = selectedCuentas;
    }

    /**
     * @return the renderedCuenta
     */
    public boolean isRenderedCuenta() {
        return renderedCuenta;
    }

    /**
     * @param renderedCuenta the renderedCuenta to set
     */
    public void setRenderedCuenta(boolean renderedCuenta) {
        this.renderedCuenta = renderedCuenta;
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
     * @return the filteredConsignacionesList
     */
    public List<Consignaciones> getFilteredConsignacionesList() {
        return filteredConsignacionesList;
    }

    /**
     * @param filteredConsignacionesList the filteredConsignacionesList to set
     */
    public void setFilteredConsignacionesList(List<Consignaciones> filteredConsignacionesList) {
        this.filteredConsignacionesList = filteredConsignacionesList;
    }

    /**
     * @return the filteredRetirosList
     */
    public List<Retiros> getFilteredRetirosList() {
        return filteredRetirosList;
    }

    /**
     * @param filteredRetirosList the filteredRetirosList to set
     */
    public void setFilteredRetirosList(List<Retiros> filteredRetirosList) {
        this.filteredRetirosList = filteredRetirosList;
    }

    /**
     * @return the selectedConsignaciones
     */
    public Consignaciones getSelectedConsignaciones() {
        return selectedConsignaciones;
    }

    /**
     * @param selectedConsignaciones the selectedConsignaciones to set
     */
    public void setSelectedConsignaciones(Consignaciones selectedConsignaciones) {
        this.selectedConsignaciones = selectedConsignaciones;
    }

    /**
     * @return the selectedRetiros
     */
    public Retiros getSelectedRetiros() {
        return selectedRetiros;
    }

    /**
     * @param selectedRetiros the selectedRetiros to set
     */
    public void setSelectedRetiros(Retiros selectedRetiros) {
        this.selectedRetiros = selectedRetiros;
    }
}
