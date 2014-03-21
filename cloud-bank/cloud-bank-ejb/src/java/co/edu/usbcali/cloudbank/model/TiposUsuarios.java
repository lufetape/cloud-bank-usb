/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.usbcali.cloudbank.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "tipos_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposUsuarios.findAll", query = "SELECT t FROM TiposUsuarios t"),
    @NamedQuery(name = "TiposUsuarios.findByTusuCodigo", query = "SELECT t FROM TiposUsuarios t WHERE t.tusuCodigo = :tusuCodigo"),
    @NamedQuery(name = "TiposUsuarios.findByTusuNombre", query = "SELECT t FROM TiposUsuarios t WHERE t.tusuNombre = :tusuNombre")})
public class TiposUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "TUSU_CODIGO")
    private Long tusuCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TUSU_NOMBRE")
    private String tusuNombre;
    @OneToMany(mappedBy = "tusuCodigo")
    private Collection<Usuarios> usuariosCollection;

    public TiposUsuarios() {
    }

    public TiposUsuarios(Long tusuCodigo) {
        this.tusuCodigo = tusuCodigo;
    }

    public TiposUsuarios(Long tusuCodigo, String tusuNombre) {
        this.tusuCodigo = tusuCodigo;
        this.tusuNombre = tusuNombre;
    }

    public Long getTusuCodigo() {
        return tusuCodigo;
    }

    public void setTusuCodigo(Long tusuCodigo) {
        this.tusuCodigo = tusuCodigo;
    }

    public String getTusuNombre() {
        return tusuNombre;
    }

    public void setTusuNombre(String tusuNombre) {
        this.tusuNombre = tusuNombre;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tusuCodigo != null ? tusuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposUsuarios)) {
            return false;
        }
        TiposUsuarios other = (TiposUsuarios) object;
        if ((this.tusuCodigo == null && other.tusuCodigo != null) || (this.tusuCodigo != null && !this.tusuCodigo.equals(other.tusuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.TiposUsuarios[ tusuCodigo=" + tusuCodigo + " ]";
    }
    
}
