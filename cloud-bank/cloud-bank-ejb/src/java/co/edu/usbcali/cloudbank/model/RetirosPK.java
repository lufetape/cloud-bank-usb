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
public class RetirosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_CODIGO")
    private long retCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUE_NUMERO")
    private String cueNumero;

    public RetirosPK() {
    }

    public RetirosPK(long retCodigo, String cueNumero) {
        this.retCodigo = retCodigo;
        this.cueNumero = cueNumero;
    }

    public long getRetCodigo() {
        return retCodigo;
    }

    public void setRetCodigo(long retCodigo) {
        this.retCodigo = retCodigo;
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
        hash += (int) retCodigo;
        hash += (cueNumero != null ? cueNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RetirosPK)) {
            return false;
        }
        RetirosPK other = (RetirosPK) object;
        if (this.retCodigo != other.retCodigo) {
            return false;
        }
        if ((this.cueNumero == null && other.cueNumero != null) || (this.cueNumero != null && !this.cueNumero.equals(other.cueNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.RetirosPK[ retCodigo=" + retCodigo + ", cueNumero=" + cueNumero + " ]";
    }
    
}
