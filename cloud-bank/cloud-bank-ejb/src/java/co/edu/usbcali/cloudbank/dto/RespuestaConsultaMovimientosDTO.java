/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO para manejar la respuesta a la consulta de movimiento de las cuentas
 * @author Felipe
 */
public class RespuestaConsultaMovimientosDTO implements Serializable {
    
    private List<MovimientoDTO> consignaciones;
    private List<MovimientoDTO> retiros;
    private EstadoDTO estado;

    /**
     * @return the consignaciones
     */
    public List<MovimientoDTO> getConsignaciones() {
        return consignaciones;
    }

    /**
     * @param consignaciones the consignaciones to set
     */
    public void setConsignaciones(List<MovimientoDTO> consignaciones) {
        this.consignaciones = consignaciones;
    }

    /**
     * @return the retiros
     */
    public List<MovimientoDTO> getRetiros() {
        return retiros;
    }

    /**
     * @param retiros the retiros to set
     */
    public void setRetiros(List<MovimientoDTO> retiros) {
        this.retiros = retiros;
    }

    /**
     * @return the estado
     */
    public EstadoDTO getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }
    
}
