/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.i_cloudbank.backing;

import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.RetirosPK;
import co.edu.usbcali.cloudbank.services.IRetiroService;
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
public class RetiroBacking extends BaseBacking implements Serializable {

    @EJB
    private IRetiroService retiroService;
    private Retiros retiros;

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
        resetRetiro();
    }

    public String onFlowProcess(FlowEvent event) {
        if (event.getOldStep().equals("paso1")) {
            if (!validarTab1(retiros)) {
                return event.getOldStep();
            }
        }
        return event.getNewStep();
    }

    public boolean validarTab1(Retiros retiro) {
        try {

            //Se verifica la confirmacion del valor:
            if (retiro.getRetValor().doubleValue() != confirmarValor.doubleValue()) {
                throw new CloudBankException(obtenerMensaje(ResourceBundles.RB_MENSAJES.RETIRO, "label_warning_valor"));
            }

            //Se verifica el retiro:
            retiroService.verificarRetiro(retiro);

            return true;
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.RETIRO, "label_error_verificando_consignacion"),
                    FacesMessage.SEVERITY_ERROR);
        }
        return false;
    }

    public void retirar() {
        try {
            //Se realiza el retiro:
            //El usuario que hace la transacción (viene de sesion):
            retiros.setUsuCedula(sesionUsuarioBacking.getUsuario());
            retiroService.retirar(retiros);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.RETIRO, "label_info_retiro_creado"),
                    FacesMessage.SEVERITY_INFO);
            resetRetiro();
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.RETIRO, "label_error_retiro"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetRetiro() {
        retiros = new Retiros();
        retiros.setRetirosPK(new RetirosPK());
        retiros.setCuentas(new Cuentas());
        confirmarValor = null;
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
