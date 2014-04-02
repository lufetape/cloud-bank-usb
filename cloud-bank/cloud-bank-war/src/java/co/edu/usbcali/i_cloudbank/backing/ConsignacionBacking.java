/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.services.IConsignacionService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FlowEvent;

/**
 * Clase que representa el Managed Bean de la vista de Consignaciones
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class ConsignacionBacking extends BaseBacking implements Serializable {
    
    @EJB
    private IConsignacionService consignacionService;
    private Consignaciones consignacion;
    
    @ManagedProperty(value = "#{buscarCuentaBacking}")
    private BuscarCuentaBacking buscarCuentaBacking;
    
    @ManagedProperty(value = "#{sesionUsuarioBacking}")
    private SesionUsuarioBacking sesionUsuarioBacking;
    
    private BigDecimal confirmarValor;

    /**
     * Método que ejecuta operaciones iniciales para el ManagedBean
     */
    @PostConstruct
    public void initialize() {
        resetConsignacion();
    }
    
    public String onFlowProcess(FlowEvent event) {  
        if (event.getOldStep().equals("paso1")) {
            if (!validarTab1(consignacion)) {
                return event.getOldStep();
            }
        }
        return event.getNewStep();  
    }
    
    public boolean validarTab1(Consignaciones consignacion){
        try {
            
            //Se verifica la confirmacion del valor:
            if(consignacion.getConValor().doubleValue() != confirmarValor.doubleValue()){
                throw new CloudBankException(obtenerMensaje(ResourceBundles.RB_MENSAJES.CONSGNACION, "label_warning_valor"));
            }
            
            //Se verifica la consignacion:
            consignacionService.verificarConsignacion(consignacion);
            
            return true;
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CONSGNACION, "label_error_verificando_consignacion"),
                    FacesMessage.SEVERITY_ERROR);
            
        }     
        return false;
    }
    
    public void consignar(){
        try {
            //Se realiza la consignacion:
            //El usuario que hace la transacción (viene de sesion):
            consignacion.setUsuCedula(sesionUsuarioBacking.getUsuario());
            consignacionService.consignar(consignacion);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CONSGNACION, "label_info_consignacion_creada"),
                    FacesMessage.SEVERITY_INFO);
            resetConsignacion();
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CONSGNACION, "label_error_consignacion"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void resetConsignacion(){
        consignacion = new Consignaciones();
        consignacion.setConsignacionesPK(new ConsignacionesPK());
        confirmarValor = null;
        buscarCuentaBacking.resetCliente();
    }
    
    public void asignarDestino(){
        if(buscarCuentaBacking.getSelectedCuenta() != null){
            consignacion.getConsignacionesPK().setCueNumero(buscarCuentaBacking.getSelectedCuenta().getCueNumero());
        } else {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
        }
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
     * @return the consignacion
     */
    public Consignaciones getConsignacion() {
        return consignacion;
    }

    /**
     * @param consignacion the consignacion to set
     */
    public void setConsignacion(Consignaciones consignacion) {
        this.consignacion = consignacion;
    }

    /**
     * @return the confirmarValor
     */
    public BigDecimal getConfirmarValor() {
        return confirmarValor;
    }

    /**
     * @param confirmarValor the confirmarValor to set
     */
    public void setConfirmarValor(BigDecimal confirmarValor) {
        this.confirmarValor = confirmarValor;
    }

    /**
     * @return the buscarCuentaBacking
     */
    public BuscarCuentaBacking getBuscarCuentaBacking() {
        return buscarCuentaBacking;
    }

    /**
     * @param buscarCuentaBacking the buscarCuentaBacking to set
     */
    public void setBuscarCuentaBacking(BuscarCuentaBacking buscarCuentaBacking) {
        this.buscarCuentaBacking = buscarCuentaBacking;
    }

    /**
     * @return the sesionUsuarioBacking
     */
    public SesionUsuarioBacking getSesionUsuarioBacking() {
        return sesionUsuarioBacking;
    }

    /**
     * @param sesionUsuarioBacking the sesionUsuarioBacking to set
     */
    public void setSesionUsuarioBacking(SesionUsuarioBacking sesionUsuarioBacking) {
        this.sesionUsuarioBacking = sesionUsuarioBacking;
    }
}
