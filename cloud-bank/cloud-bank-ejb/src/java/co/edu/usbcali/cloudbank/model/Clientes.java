/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.model;

import java.io.Serializable;
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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByCliId", query = "SELECT c FROM Clientes c WHERE c.cliId = :cliId"),
    @NamedQuery(name = "Clientes.findByCliNombre", query = "SELECT c FROM Clientes c WHERE c.cliNombre = :cliNombre"),
    @NamedQuery(name = "Clientes.findByCliDireccion", query = "SELECT c FROM Clientes c WHERE c.cliDireccion = :cliDireccion"),
    @NamedQuery(name = "Clientes.findByCliTelefono", query = "SELECT c FROM Clientes c WHERE c.cliTelefono = :cliTelefono"),
    @NamedQuery(name = "Clientes.findByCliMail", query = "SELECT c FROM Clientes c WHERE c.cliMail = :cliMail")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CLI_ID")
    private Long cliId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLI_NOMBRE")
    private String cliNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLI_DIRECCION")
    private String cliDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CLI_TELEFONO")
    private String cliTelefono;
    @Size(max = 50)
    @Column(name = "CLI_MAIL")
    private String cliMail;
    @JoinColumn(name = "TDOC_CODIGO", referencedColumnName = "TDOC_CODIGO")
    @ManyToOne(optional = false)
    private TiposDocumentos tdocCodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliId")
    private Collection<Cuentas> cuentasCollection;

    public Clientes() {
    }

    public Clientes(Long cliId) {
        this.cliId = cliId;
    }

    public Clientes(Long cliId, String cliNombre, String cliDireccion, String cliTelefono) {
        this.cliId = cliId;
        this.cliNombre = cliNombre;
        this.cliDireccion = cliDireccion;
        this.cliTelefono = cliTelefono;
    }

    public Long getCliId() {
        return cliId;
    }

    public void setCliId(Long cliId) {
        this.cliId = cliId;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliTelefono() {
        return cliTelefono;
    }

    public void setCliTelefono(String cliTelefono) {
        this.cliTelefono = cliTelefono;
    }

    public String getCliMail() {
        return cliMail;
    }

    public void setCliMail(String cliMail) {
        this.cliMail = cliMail;
    }

    public TiposDocumentos getTdocCodigo() {
        return tdocCodigo;
    }

    public void setTdocCodigo(TiposDocumentos tdocCodigo) {
        this.tdocCodigo = tdocCodigo;
    }

    @XmlTransient
    public Collection<Cuentas> getCuentasCollection() {
        return cuentasCollection;
    }

    public void setCuentasCollection(Collection<Cuentas> cuentasCollection) {
        this.cuentasCollection = cuentasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cliId != null ? cliId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.cliId == null && other.cliId != null) || (this.cliId != null && !this.cliId.equals(other.cliId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.Clientes[ cliId=" + cliId + " ]";
    }
    
}
