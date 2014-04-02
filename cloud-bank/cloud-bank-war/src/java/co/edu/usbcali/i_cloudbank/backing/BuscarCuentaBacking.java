/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Clientes;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.services.IClienteService;
import co.edu.usbcali.cloudbank.services.ICuentaService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ObjectKeys;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.panelgrid.PanelGrid;

/**
 * Clase que representa el Managed Bean de la vista de Busqueda de cuentas
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class BuscarCuentaBacking extends BaseBacking implements Serializable {
    
    @EJB
    private ICuentaService cuentaService;
    @EJB
    private IClienteService clienteService;
    private Clientes selectedCliente;
    private Cuentas selectedCuenta;
    
    private PanelGrid panelCuentas;

    /**
     * Método que ejecuta operaciones iniciales para el ManagedBean
     */
    @PostConstruct
    public void initialize() {
        panelCuentas = new PanelGrid();
        resetCliente();
    }

    /**
     * Método que completa clientes
     * @param query
     * @return Listado de clientes encontrados
     */
    public List<Clientes> completarClientes(String query) {
        List<Clientes> clientes = new ArrayList<>();
        try {
            removeValueFromSession(ObjectKeys.KEY_CLIENTES);
            clientes = clienteService.consultarPorPalabraClave(query);
            putValueInSession(ObjectKeys.KEY_CLIENTES, clientes);
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
        return clientes;
    }

    public void listarCuentas() {

        try {               
            selectedCliente.setCuentasCollection(cuentaService.consultarPorCliente(selectedCliente.getCliId()));
            if(selectedCliente.getCuentasCollection() != null){
                panelCuentas.setRendered(true);
            } else {
                panelCuentas.setRendered(false);
                this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_warning_no_cuentas"),
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
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_listar_por_cliente"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void resetCliente(){
        selectedCliente = new Clientes();
        panelCuentas.setRendered(false);
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
     * @return the panelCuentas
     */
    public PanelGrid getPanelCuentas() {
        return panelCuentas;
    }

    /**
     * @param panelCuentas the panelCuentas to set
     */
    public void setPanelCuentas(PanelGrid panelCuentas) {
        this.panelCuentas = panelCuentas;
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
}
