/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "retiros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retiros.findAll", query = "SELECT r FROM Retiros r"),
    @NamedQuery(name = "Retiros.findByRetCodigo", query = "SELECT r FROM Retiros r WHERE r.retirosPK.retCodigo = :retCodigo"),
    @NamedQuery(name = "Retiros.findByCueNumero", query = "SELECT r FROM Retiros r WHERE r.retirosPK.cueNumero = :cueNumero"),
    @NamedQuery(name = "Retiros.findByRetValor", query = "SELECT r FROM Retiros r WHERE r.retValor = :retValor"),
    @NamedQuery(name = "Retiros.findByRetFecha", query = "SELECT r FROM Retiros r WHERE r.retFecha = :retFecha"),
    @NamedQuery(name = "Retiros.findByRetDescripcion", query = "SELECT r FROM Retiros r WHERE r.retDescripcion = :retDescripcion")})
public class Retiros implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RetirosPK retirosPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_VALOR")
    private BigDecimal retValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RET_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RET_DESCRIPCION")
    private String retDescripcion;
    @JoinColumn(name = "CUE_NUMERO", referencedColumnName = "CUE_NUMERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuentas cuentas;
    @JoinColumn(name = "USU_CEDULA", referencedColumnName = "USU_CEDULA")
    @ManyToOne
    private Usuarios usuCedula;

    public Retiros() {
    }

    public Retiros(RetirosPK retirosPK) {
        this.retirosPK = retirosPK;
    }

    public Retiros(RetirosPK retirosPK, BigDecimal retValor, Date retFecha, String retDescripcion) {
        this.retirosPK = retirosPK;
        this.retValor = retValor;
        this.retFecha = retFecha;
        this.retDescripcion = retDescripcion;
    }

    public Retiros(long retCodigo, String cueNumero) {
        this.retirosPK = new RetirosPK(retCodigo, cueNumero);
    }

    public RetirosPK getRetirosPK() {
        return retirosPK;
    }

    public void setRetirosPK(RetirosPK retirosPK) {
        this.retirosPK = retirosPK;
    }

    public BigDecimal getRetValor() {
        return retValor;
    }

    public void setRetValor(BigDecimal retValor) {
        this.retValor = retValor;
    }

    public Date getRetFecha() {
        return retFecha;
    }

    public void setRetFecha(Date retFecha) {
        this.retFecha = retFecha;
    }

    public String getRetDescripcion() {
        return retDescripcion;
    }

    public void setRetDescripcion(String retDescripcion) {
        this.retDescripcion = retDescripcion;
    }

    public Cuentas getCuentas() {
        return cuentas;
    }

    public void setCuentas(Cuentas cuentas) {
        this.cuentas = cuentas;
    }

    public Usuarios getUsuCedula() {
        return usuCedula;
    }

    public void setUsuCedula(Usuarios usuCedula) {
        this.usuCedula = usuCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (retirosPK != null ? retirosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retiros)) {
            return false;
        }
        Retiros other = (Retiros) object;
        if ((this.retirosPK == null && other.retirosPK != null) || (this.retirosPK != null && !this.retirosPK.equals(other.retirosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.Retiros[ retirosPK=" + retirosPK + " ]";
    }
    
}
