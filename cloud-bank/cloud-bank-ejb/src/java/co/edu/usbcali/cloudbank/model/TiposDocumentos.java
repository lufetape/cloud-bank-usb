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
@Table(name = "tipos_documentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposDocumentos.findAll", query = "SELECT t FROM TiposDocumentos t"),
    @NamedQuery(name = "TiposDocumentos.findByTdocCodigo", query = "SELECT t FROM TiposDocumentos t WHERE t.tdocCodigo = :tdocCodigo"),
    @NamedQuery(name = "TiposDocumentos.findByTdocNombre", query = "SELECT t FROM TiposDocumentos t WHERE t.tdocNombre = :tdocNombre")})
public class TiposDocumentos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TDOC_CODIGO")
    private Long tdocCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TDOC_NOMBRE")
    private String tdocNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tdocCodigo")
    private Collection<Clientes> clientesCollection;

    public TiposDocumentos() {
    }

    public TiposDocumentos(Long tdocCodigo) {
        this.tdocCodigo = tdocCodigo;
    }

    public TiposDocumentos(Long tdocCodigo, String tdocNombre) {
        this.tdocCodigo = tdocCodigo;
        this.tdocNombre = tdocNombre;
    }

    public Long getTdocCodigo() {
        return tdocCodigo;
    }

    public void setTdocCodigo(Long tdocCodigo) {
        this.tdocCodigo = tdocCodigo;
    }

    public String getTdocNombre() {
        return tdocNombre;
    }

    public void setTdocNombre(String tdocNombre) {
        this.tdocNombre = tdocNombre;
    }

    @XmlTransient
    public Collection<Clientes> getClientesCollection() {
        return clientesCollection;
    }

    public void setClientesCollection(Collection<Clientes> clientesCollection) {
        this.clientesCollection = clientesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdocCodigo != null ? tdocCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposDocumentos)) {
            return false;
        }
        TiposDocumentos other = (TiposDocumentos) object;
        if ((this.tdocCodigo == null && other.tdocCodigo != null) || (this.tdocCodigo != null && !this.tdocCodigo.equals(other.tdocCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.TiposDocumentos[ tdocCodigo=" + tdocCodigo + " ]";
    }
    
}
