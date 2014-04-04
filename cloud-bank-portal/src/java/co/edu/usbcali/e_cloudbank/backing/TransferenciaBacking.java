/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.backing;

import co.edu.usbcali.e_cloudbank.services.ITransferenciaService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Clase que representa el Managed Bean de la vista de Cuentas del Cliente
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class TransferenciaBacking extends BaseBacking implements Serializable {

    @EJB
    private ITransferenciaService transferenciaService;

    private CuentaDTO selectedCuentaOrigen;
    private CuentaDTO selectedCuentaDestino;

    @ManagedProperty(value = "#{cuentaBacking}")
    private CuentaBacking cuentaBacking;
    
    @ManagedProperty(value = "#{sesionUsuarioBacking}")
    private SesionUsuarioBacking sesionUsuarioBacking;

    private String clave;
    private Double valor;
    private Double confirmeValor;

    @PostConstruct
    public void init() {
        resetForm();
        cuentaBacking.listarCuentas();
    }

    public void realizarTransferencia() {

        //Validando el valor:
        if (!Objects.equals(valor, confirmeValor)) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_warning_valor"),
                    FacesMessage.SEVERITY_WARN);
            return;
        }

        try {
            //ID cliente (de sesion)
            long idCliente = sesionUsuarioBacking.getUsuario().getIdentificacion();
            if(selectedCuentaOrigen == null){
                selectedCuentaOrigen = new CuentaDTO();
            }
            if(selectedCuentaDestino == null){
                selectedCuentaDestino = new CuentaDTO();
            }
            transferenciaService.realizarTransferencia(idCliente, selectedCuentaOrigen, clave, selectedCuentaDestino, valor);
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_info_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.TRANSFERENCIA, "label_info_transferencia_creada"),
                    FacesMessage.SEVERITY_INFO);
            resetForm();
            cuentaBacking.listarCuentas();
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

    public void resetForm() {
        selectedCuentaOrigen = new CuentaDTO();
        selectedCuentaDestino = new CuentaDTO();
        clave = null;
        valor = 0D;
        confirmeValor = 0D;
    }

    /**
     * @return the selectedCuentaOrigen
     */
    public CuentaDTO getSelectedCuentaOrigen() {
        return selectedCuentaOrigen;
    }

    /**
     * @param selectedCuentaOrigen the selectedCuentaOrigen to set
     */
    public void setSelectedCuentaOrigen(CuentaDTO selectedCuentaOrigen) {
        this.selectedCuentaOrigen = selectedCuentaOrigen;
    }

    /**
     * @return the selectedCuentaDestino
     */
    public CuentaDTO getSelectedCuentaDestino() {
        return selectedCuentaDestino;
    }

    /**
     * @param selectedCuentaDestino the selectedCuentaDestino to set
     */
    public void setSelectedCuentaDestino(CuentaDTO selectedCuentaDestino) {
        this.selectedCuentaDestino = selectedCuentaDestino;
    }

    /**
     * @return the cuentaBacking
     */
    public CuentaBacking getCuentaBacking() {
        return cuentaBacking;
    }

    /**
     * @param cuentaBacking the cuentaBacking to set
     */
    public void setCuentaBacking(CuentaBacking cuentaBacking) {
        this.cuentaBacking = cuentaBacking;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the confirmeValor
     */
    public Double getConfirmeValor() {
        return confirmeValor;
    }

    /**
     * @param confirmeValor the confirmeValor to set
     */
    public void setConfirmeValor(Double confirmeValor) {
        this.confirmeValor = confirmeValor;
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
