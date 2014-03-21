package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Usuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(TiposUsuarios.class)
public class TiposUsuarios_ { 

    public static volatile SingularAttribute<TiposUsuarios, Long> tusuCodigo;
    public static volatile SingularAttribute<TiposUsuarios, String> tusuNombre;
    public static volatile CollectionAttribute<TiposUsuarios, Usuarios> usuariosCollection;

}