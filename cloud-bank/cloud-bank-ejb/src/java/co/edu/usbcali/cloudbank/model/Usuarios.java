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
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsuCedula", query = "SELECT u FROM Usuarios u WHERE u.usuCedula = :usuCedula"),
    @NamedQuery(name = "Usuarios.findByUsuNombre", query = "SELECT u FROM Usuarios u WHERE u.usuNombre = :usuNombre"),
    @NamedQuery(name = "Usuarios.findByUsuLogin", query = "SELECT u FROM Usuarios u WHERE u.usuLogin = :usuLogin"),
    @NamedQuery(name = "Usuarios.findByUsuClave", query = "SELECT u FROM Usuarios u WHERE u.usuClave = :usuClave")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USU_CEDULA")
    private Long usuCedula;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USU_NOMBRE")
    private String usuNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USU_LOGIN")
    private String usuLogin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USU_CLAVE")
    private String usuClave;
    @OneToMany(mappedBy = "usuCedula")
    private Collection<Consignaciones> consignacionesCollection;
    @OneToMany(mappedBy = "usuCedula")
    private Collection<Retiros> retirosCollection;
    @JoinColumn(name = "TUSU_CODIGO", referencedColumnName = "TUSU_CODIGO")
    @ManyToOne
    private TiposUsuarios tusuCodigo;

    public Usuarios() {
    }

    public Usuarios(Long usuCedula) {
        this.usuCedula = usuCedula;
    }

    public Usuarios(Long usuCedula, String usuNombre, String usuLogin, String usuClave) {
        this.usuCedula = usuCedula;
        this.usuNombre = usuNombre;
        this.usuLogin = usuLogin;
        this.usuClave = usuClave;
    }

    public Long getUsuCedula() {
        return usuCedula;
    }

    public void setUsuCedula(Long usuCedula) {
        this.usuCedula = usuCedula;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuClave() {
        return usuClave;
    }

    public void setUsuClave(String usuClave) {
        this.usuClave = usuClave;
    }

    @XmlTransient
    public Collection<Consignaciones> getConsignacionesCollection() {
        return consignacionesCollection;
    }

    public void setConsignacionesCollection(Collection<Consignaciones> consignacionesCollection) {
        this.consignacionesCollection = consignacionesCollection;
    }

    @XmlTransient
    public Collection<Retiros> getRetirosCollection() {
        return retirosCollection;
    }

    public void setRetirosCollection(Collection<Retiros> retirosCollection) {
        this.retirosCollection = retirosCollection;
    }

    public TiposUsuarios getTusuCodigo() {
        return tusuCodigo;
    }

    public void setTusuCodigo(TiposUsuarios tusuCodigo) {
        this.tusuCodigo = tusuCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuCedula != null ? usuCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usuCedula == null && other.usuCedula != null) || (this.usuCedula != null && !this.usuCedula.equals(other.usuCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.usbcali.cloudbank.model.Usuarios[ usuCedula=" + usuCedula + " ]";
    }
    
}
