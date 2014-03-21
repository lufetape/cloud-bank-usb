package co.edu.usbcali.cloudbank.model;

import co.edu.usbcali.cloudbank.model.Cuentas;
import co.edu.usbcali.cloudbank.model.TiposDocumentos;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-03-21T09:23:23")
@StaticMetamodel(Clientes.class)
public class Clientes_ { 

    public static volatile CollectionAttribute<Clientes, Cuentas> cuentasCollection;
    public static volatile SingularAttribute<Clientes, String> cliMail;
    public static volatile SingularAttribute<Clientes, Long> cliId;
    public static volatile SingularAttribute<Clientes, String> cliDireccion;
    public static volatile SingularAttribute<Clientes, String> cliTelefono;
    public static volatile SingularAttribute<Clientes, TiposDocumentos> tdocCodigo;
    public static volatile SingularAttribute<Clientes, String> cliNombre;

}