/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Felipe
 */
@Embeddable
public class ConsignacionesPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_CODIGO")
    private long conCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUE_NUMERO")
    private String cueNumero;

    public ConsignacionesPK() {
    }

    public ConsignacionesPK(long conCodigo, String cueNumero) {
        this.conCodigo = conCodigo;
        this.cueNumero = cueNumero;
    }

    public long getConCodigo() {
        return conCodigo;
    }

    public void setConCodigo(long conCodigo) {
        this.conCodigo = conCodigo;
    }

    public String getCueNumero() {
        return cueNumero;
    }

    public void setCueNumero(String cueNumero) {
        this.cueNumero = cueNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) conCodigo;
        hash += (cueNumero != null ? cueNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsignacionesPK)) {
            return false;
        }
        ConsignacionesPK other = (ConsignacionesPK) object;
        if (this.conCodigo != other.conCodigo) {
            return false;
        }
        if ((this.cueNumero == null && other.cueNumero != null) || (this.cueNumero != null && !this.cueNumero.equals(other.cueNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.ConsignacionesPK[ conCodigo=" + conCodigo + ", cueNumero=" + cueNumero + " ]";
    }
    
}
