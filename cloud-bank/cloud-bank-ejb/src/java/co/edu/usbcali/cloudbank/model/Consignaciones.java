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
@Table(name = "consignaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consignaciones.findAll", query = "SELECT c FROM Consignaciones c"),
    @NamedQuery(name = "Consignaciones.findByConCodigo", query = "SELECT c FROM Consignaciones c WHERE c.consignacionesPK.conCodigo = :conCodigo"),
    @NamedQuery(name = "Consignaciones.findByCueNumero", query = "SELECT c FROM Consignaciones c WHERE c.consignacionesPK.cueNumero = :cueNumero"),
    @NamedQuery(name = "Consignaciones.findByConValor", query = "SELECT c FROM Consignaciones c WHERE c.conValor = :conValor"),
    @NamedQuery(name = "Consignaciones.findByConFecha", query = "SELECT c FROM Consignaciones c WHERE c.conFecha = :conFecha"),
    @NamedQuery(name = "Consignaciones.findByConDescripcion", query = "SELECT c FROM Consignaciones c WHERE c.conDescripcion = :conDescripcion")})
public class Consignaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConsignacionesPK consignacionesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_VALOR")
    private BigDecimal conValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CON_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date conFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CON_DESCRIPCION")
    private String conDescripcion;
    @JoinColumn(name = "CUE_NUMERO", referencedColumnName = "CUE_NUMERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuentas cuentas;
    @JoinColumn(name = "USU_CEDULA", referencedColumnName = "USU_CEDULA")
    @ManyToOne
    private Usuarios usuCedula;

    public Consignaciones() {
    }

    public Consignaciones(ConsignacionesPK consignacionesPK) {
        this.consignacionesPK = consignacionesPK;
    }

    public Consignaciones(ConsignacionesPK consignacionesPK, BigDecimal conValor, Date conFecha, String conDescripcion) {
        this.consignacionesPK = consignacionesPK;
        this.conValor = conValor;
        this.conFecha = conFecha;
        this.conDescripcion = conDescripcion;
    }

    public Consignaciones(long conCodigo, String cueNumero) {
        this.consignacionesPK = new ConsignacionesPK(conCodigo, cueNumero);
    }

    public ConsignacionesPK getConsignacionesPK() {
        return consignacionesPK;
    }

    public void setConsignacionesPK(ConsignacionesPK consignacionesPK) {
        this.consignacionesPK = consignacionesPK;
    }

    public BigDecimal getConValor() {
        return conValor;
    }

    public void setConValor(BigDecimal conValor) {
        this.conValor = conValor;
    }

    public Date getConFecha() {
        return conFecha;
    }

    public void setConFecha(Date conFecha) {
        this.conFecha = conFecha;
    }

    public String getConDescripcion() {
        return conDescripcion;
    }

    public void setConDescripcion(String conDescripcion) {
        this.conDescripcion = conDescripcion;
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
        hash += (consignacionesPK != null ? consignacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consignaciones)) {
            return false;
        }
        Consignaciones other = (Consignaciones) object;
        if ((this.consignacionesPK == null && other.consignacionesPK != null) || (this.consignacionesPK != null && !this.consignacionesPK.equals(other.consignacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.Consignaciones[ consignacionesPK=" + consignacionesPK + " ]";
    }
    
}
