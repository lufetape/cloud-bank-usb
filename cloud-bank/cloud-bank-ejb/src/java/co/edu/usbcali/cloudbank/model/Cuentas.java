/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "cuentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuentas.findAll", query = "SELECT c FROM Cuentas c"),
    @NamedQuery(name = "Cuentas.findByCueNumero", query = "SELECT c FROM Cuentas c WHERE c.cueNumero = :cueNumero"),
    @NamedQuery(name = "Cuentas.findByCueSaldo", query = "SELECT c FROM Cuentas c WHERE c.cueSaldo = :cueSaldo"),
    @NamedQuery(name = "Cuentas.findByCueActiva", query = "SELECT c FROM Cuentas c WHERE c.cueActiva = :cueActiva"),
    @NamedQuery(name = "Cuentas.findByCueClave", query = "SELECT c FROM Cuentas c WHERE c.cueClave = :cueClave")})
public class Cuentas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CUE_NUMERO")
    private String cueNumero;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUE_SALDO")
    private BigDecimal cueSaldo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "CUE_ACTIVA")
    private String cueActiva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CUE_CLAVE")
    private String cueClave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentas")
    private Collection<Consignaciones> consignacionesCollection;
    @JoinColumn(name = "CLI_ID", referencedColumnName = "CLI_ID")
    @ManyToOne(optional = false)
    private Clientes cliId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentas")
    private Collection<Retiros> retirosCollection;

    public Cuentas() {
    }

    public Cuentas(String cueNumero) {
        this.cueNumero = cueNumero;
    }

    public Cuentas(String cueNumero, BigDecimal cueSaldo, String cueActiva, String cueClave) {
        this.cueNumero = cueNumero;
        this.cueSaldo = cueSaldo;
        this.cueActiva = cueActiva;
        this.cueClave = cueClave;
    }

    public String getCueNumero() {
        return cueNumero;
    }

    public void setCueNumero(String cueNumero) {
        this.cueNumero = cueNumero;
    }

    public BigDecimal getCueSaldo() {
        return cueSaldo;
    }

    public void setCueSaldo(BigDecimal cueSaldo) {
        this.cueSaldo = cueSaldo;
    }

    public String getCueActiva() {
        return cueActiva;
    }

    public void setCueActiva(String cueActiva) {
        this.cueActiva = cueActiva;
    }

    public String getCueClave() {
        return cueClave;
    }

    public void setCueClave(String cueClave) {
        this.cueClave = cueClave;
    }

    @XmlTransient
    public Collection<Consignaciones> getConsignacionesCollection() {
        return consignacionesCollection;
    }

    public void setConsignacionesCollection(Collection<Consignaciones> consignacionesCollection) {
        this.consignacionesCollection = consignacionesCollection;
    }

    public Clientes getCliId() {
        return cliId;
    }

    public void setCliId(Clientes cliId) {
        this.cliId = cliId;
    }

    @XmlTransient
    public Collection<Retiros> getRetirosCollection() {
        return retirosCollection;
    }

    public void setRetirosCollection(Collection<Retiros> retirosCollection) {
        this.retirosCollection = retirosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cueNumero != null ? cueNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuentas)) {
            return false;
        }
        Cuentas other = (Cuentas) object;
        if ((this.cueNumero == null && other.cueNumero != null) || (this.cueNumero != null && !this.cueNumero.equals(other.cueNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.Cuentas[ cueNumero=" + cueNumero + " ]";
    }
    
}
