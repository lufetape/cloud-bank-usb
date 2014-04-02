/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.ConsignacionesPK;
import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.RetirosPK;
import co.edu.usbcali.cloudbank.model.Usuarios;
import co.edu.usbcali.cloudbank.services.ITransferenciaService;
import co.edu.usbcali.cloudbank.util.CloudBankException;
import co.edu.usbcali.i_cloudbank.utils.ResourceBundles;
import java.io.Serializable;
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
public class TransferenciaBacking extends BaseBacking implements Serializable {

    @EJB
    private ITransferenciaService transferenciaService;
    private Retiros retiros;
    private Consignaciones consignacion;

    @ManagedProperty(value = "#{buscarCuentaBacking}")
    private BuscarCuentaBacking buscarCuentaBacking;
    
    @ManagedProperty(value = "#{sesionUsuarioBacking}")
    private SesionUsuarioBacking sesionUsuarioBacking;

    /**
     * Método que ejecuta operaciones iniciales para el ManagedBean
     */
    @PostConstruct
    public void initialize() {
        reset();
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("paso1")) {
            if (!validarTab1(retiros, consignacion)) {
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    public boolean validarTab1(Retiros retiro, Consignaciones consignacion) {
        try {
            if (retiro.getRetValor().doubleValue() != consignacion.getConValor().doubleValue()) {
                throw new CloudBankException(obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_valor"));
            }
            //Se verifica la transferencia:
            transferenciaService.verificarTransferencia(consignacion, retiro);
            return true;
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_error_verificando_transferencia"),
                    FacesMessage.SEVERITY_ERROR);
        }
        return false;
    }

    public void transferir() {
        try {
            //Se realiza la transferencia:

            //El usuario que hace la transacción:
            consignacion.setUsuCedula(sesionUsuarioBacking.getUsuario());//Pendiente tomar de la sesion
            retiros.setUsuCedula(sesionUsuarioBacking.getUsuario());//Pendiente tomar de la sesion

            //Se hace la transferencia
            transferenciaService.transferir(consignacion, retiros);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_info_transferencia_creada"),
                    FacesMessage.SEVERITY_INFO);
            reset();
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_error_transferencia"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void reset() {
        resetConsignacion();
        resetRetiro();
    }

    public void resetConsignacion() {
        consignacion = new Consignaciones();
        consignacion.setConsignacionesPK(new ConsignacionesPK());
    }

    public void resetRetiro() {
        retiros = new Retiros();
        retiros.setRetirosPK(new RetirosPK());
        retiros.setCuentas(new Cuentas());
        buscarCuentaBacking.resetCliente();
    }

    public void asignarOrigen() {
        if (buscarCuentaBacking.getSelectedCuenta() != null) {
            retiros.getRetirosPK().setCueNumero(buscarCuentaBacking.getSelectedCuenta().getCueNumero());
        } else {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
        }
    }

    public void asignarDestino() {
        if (buscarCuentaBacking.getSelectedCuenta() != null) {
            consignacion.getConsignacionesPK().setCueNumero(buscarCuentaBacking.getSelectedCuenta().getCueNumero());
        } else {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_warning_seleccionar_registro"),
                    FacesMessage.SEVERITY_WARN);
        }
    }

    /**
     * @return the retiros
     */
    public Retiros getRetiros() {
        return retiros;
    }

    /**
     * @param retiros the retiros to set
     */
    public void setRetiros(Retiros retiros) {
        this.retiros = retiros;
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
     * @return the transferenciaService
     */
    public ITransferenciaService getTransferenciaService() {
        return transferenciaService;
    }

    /**
     * @param transferenciaService the transferenciaService to set
     */
    public void setTransferenciaService(ITransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
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
