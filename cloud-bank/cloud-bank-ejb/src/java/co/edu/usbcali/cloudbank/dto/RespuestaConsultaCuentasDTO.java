/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO para entregar la respuesta de la coonsulta de cuentas de un cliente
 * 
 * @author Felipe
 */
public class RespuestaConsultaCuentasDTO implements Serializable {
    
    private List<CuentaDTO> cuentas;
    private EstadoDTO estado;

    /**
     * @return the cuentas
     */
    public List<CuentaDTO> getCuentas() {
        return cuentas;
    }

    /**
     * @param cuentas the cuentas to set
     */
    public void setCuentas(List<CuentaDTO> cuentas) {
        this.cuentas = cuentas;
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
