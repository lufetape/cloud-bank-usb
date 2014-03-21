package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Consignaciones;
import co.edu.usbcali.cloudbank.model.Retiros;
import co.edu.usbcali.cloudbank.model.TiposUsuarios;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, Long> usuCedula;
    public static volatile SingularAttribute<Usuarios, String> usuLogin;
    public static volatile CollectionAttribute<Usuarios, Consignaciones> consignacionesCollection;
    public static volatile SingularAttribute<Usuarios, TiposUsuarios> tusuCodigo;
    public static volatile CollectionAttribute<Usuarios, Retiros> retirosCollection;
    public static volatile SingularAttribute<Usuarios, String> usuNombre;
    public static volatile SingularAttribute<Usuarios, String> usuClave;

}