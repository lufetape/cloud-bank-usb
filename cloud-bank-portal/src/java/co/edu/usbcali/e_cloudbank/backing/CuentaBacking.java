/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usbcali.e_cloudbank.backing;

import co.edu.usbcali.e_cloudbank.services.ICuentaService;
import co.edu.usbcali.e_cloudbank.utils.CloudBankException;
import co.edu.usbcali.e_cloudbank.utils.ResourceBundles;
import co.edu.usbcali.e_cloudbank.wsclient.CuentaDTO;
import co.edu.usbcali.e_cloudbank.wsclient.MovimientoDTO;
import co.edu.usbcali.e_cloudbank.wsclient.RespuestaConsultaMovimientosDTO;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.component.row.Row;

/**
 * Clase que representa el Managed Bean de la vista de Cuentas del Cliente
 *
 * @author Felipe
 */
@ManagedBean
@ViewScoped
public class CuentaBacking extends BaseBacking implements Serializable {

    @EJB
    private ICuentaService cuentaService;

    private List<CuentaDTO> cuentasList;
    private CuentaDTO selectedCuenta;

    private List<MovimientoDTO> consignacionesList;
    private List<MovimientoDTO> retirosList;

    private Date dateInicial;
    private Date dateFinal;

    private Row rowMovimientos;

    @PostConstruct
    public void init() {
        listarCuentas();
        rowMovimientos = new Row();
        rowMovimientos.setRendered(false);
    }

    public void listarCuentas() {
        try {
            //ID cliente (de sesion)
            long idCliente = 251234L;
            cuentasList = cuentaService.listarCuentas(idCliente).getCuentas();
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

    public void listarMovimientos() {
        //Ultimos 30 dias
        Calendar fechaIncial = Calendar.getInstance();
        fechaIncial.set(Calendar.DATE, fechaIncial.get(Calendar.DATE) - 30);

        Calendar fechaFinal = Calendar.getInstance();

        dateInicial = fechaIncial.getTime();
        dateFinal = fechaFinal.getTime();
        filtrarMovimientos();
    }

    public void filtrarMovimientos() {
        try {
            //ID cliente (de sesion)
            long idCliente = 251234L;
            RespuestaConsultaMovimientosDTO respuestaConsultaMovimientosDTO = cuentaService.listarMovimientos(idCliente, selectedCuenta.getNumero(), dateInicial, dateFinal);
            consignacionesList = respuestaConsultaMovimientosDTO.getConsignaciones();
            retirosList = respuestaConsultaMovimientosDTO.getRetiros();
            rowMovimientos.setRendered(true);
            
            //Ordenamiento inicial por defecto:
            Comparator<MovimientoDTO> comparator = new Comparator<MovimientoDTO>() {
                @Override
                public int compare(MovimientoDTO t1, MovimientoDTO t2) {
                    return t2.getFecha().compareTo(t1.getFecha());
                }
            };
            Collections.sort(consignacionesList,comparator);
            Collections.sort(retirosList,comparator);
            
        } catch (CloudBankException cbe) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_warning_titulo"),
                    cbe.getMessage(),
                    FacesMessage.SEVERITY_WARN);
        } catch (Exception e) {
            this.mostrarMensaje(null,
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.COMUN, "label_error_titulo"),
                    obtenerMensaje(ResourceBundles.RB_MENSAJES.CUENTA, "label_error_listar_movimentos"),
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    public void resetFiltros() {
        dateInicial = null;
        dateFinal = null;
    }

    /**
     * @return the cuentasList
     */
    public List<CuentaDTO> getCuentasList() {
        return cuentasList;
    }

    /**
     * @param cuentasList the cuentasList to set
     */
    public void setCuentasList(List<CuentaDTO> cuentasList) {
        this.cuentasList = cuentasList;
    }

    /**
     * @return the selectedCuenta
     */
    public CuentaDTO getSelectedCuenta() {
        return selectedCuenta;
    }

    /**
     * @param selectedCuenta the selectedCuenta to set
     */
    public void setSelectedCuenta(CuentaDTO selectedCuenta) {
        this.selectedCuenta = selectedCuenta;
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

    /**
     * @return the consignacionesList
     */
    public List<MovimientoDTO> getConsignacionesList() {
        return consignacionesList;
    }

    /**
     * @param consignacionesList the consignacionesList to set
     */
    public void setConsignacionesList(List<MovimientoDTO> consignacionesList) {
        this.consignacionesList = consignacionesList;
    }

    /**
     * @return the retirosList
     */
    public List<MovimientoDTO> getRetirosList() {
        return retirosList;
    }

    /**
     * @param retirosList the retirosList to set
     */
    public void setRetirosList(List<MovimientoDTO> retirosList) {
        this.retirosList = retirosList;
    }

    /**
     * @return the rowMovimientos
     */
    public Row getRowMovimientos() {
        return rowMovimientos;
    }

    /**
     * @param rowMovimientos the rowMovimientos to set
     */
    public void setRowMovimientos(Row rowMovimientos) {
        this.rowMovimientos = rowMovimientos;
    }
}
